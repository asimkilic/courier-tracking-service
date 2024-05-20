package com.asimkilic.courier.courier.repository;

import com.asimkilic.courier.common.repository.BaseJpaRepository;
import com.asimkilic.courier.courier.entity.CourierLocation;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierLocationRepository extends BaseJpaRepository<CourierLocation, Long> {
}
