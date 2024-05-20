package com.asimkilic.courier.store.repository;

import com.asimkilic.courier.common.repository.BaseJpaRepository;
import com.asimkilic.courier.store.entity.StoreEntrance;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreEntranceRepository extends BaseJpaRepository<StoreEntrance, Long> {

    @Query("""
            SELECT se.entranceTime
            FROM StoreEntrance se
            WHERE se.courier.id = :courierId
            ORDER BY se.entranceTime DESC
            """)
    List<Instant> getLastEntranceTime(@Param("courierId") Long courierId, Pageable pageable);

    @Query("""
                SELECT SUM(se.totalTrip)
                FROM StoreEntrance se
                WHERE se.courier.id = :courierId
            """)
    double getTotalTravelDistanceByCourierId(@Param("courierId") Long courierId);
}
