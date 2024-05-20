package com.asimkilic.courier.store.service;

import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.store.dto.StoreDto;
import com.asimkilic.courier.store.entity.Store;
import com.asimkilic.courier.store.repository.StoreRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreDto findNearestStoreWithinRadiusByTime(LocationRequest event, Double radius, Instant limitTime) {
        return storeRepository.findNearestStoreWithinRadiusByTime(event.latitude(), event.longitude(), radius, event.courierId(), limitTime);
    }

    public Store getReferenceById(Long id) {
        return storeRepository.getReferenceById(id);
    }
}
