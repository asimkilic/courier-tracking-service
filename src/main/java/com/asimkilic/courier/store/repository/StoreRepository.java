package com.asimkilic.courier.store.repository;

import com.asimkilic.courier.store.dto.StoreDto;
import com.asimkilic.courier.store.entity.Store;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> getByName(String name);

    @Query(value = """
            WITH courier_entrances AS (
                SELECT se.store_id AS storeId
                FROM store_entrance se
                WHERE se.courier_id = :courierId AND se.entrance_time >= :limitTime
                GROUP BY se.store_id)
            SELECT 
                s.id AS id,
                ST_DistanceSphere(ST_MakePoint(:longitude, :latitude), ST_MakePoint(s.longitude, s.latitude)) AS distanceToStore,
                s.name AS storeName
            FROM store s 
            WHERE s.id NOT IN (SELECT storeId FROM courier_entrances) 
                AND ST_DistanceSphere(ST_MakePoint(:longitude, :latitude), ST_MakePoint(s.longitude, s.latitude)) <= :radius 
            ORDER BY distanceToStore DESC limit 1
            """, nativeQuery = true)
    StoreDto findNearestStoreWithinRadiusByTime(@Param("latitude") Double courierLatitude,
                                                @Param("longitude") Double courierLongitude,
                                                @Param("radius") Double radius,
                                                @Param("courierId") Long courierId,
                                                @Param("limitTime") Instant limitTime);

}
