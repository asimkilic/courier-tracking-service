package com.asimkilic.courier.store.handler;

import com.asimkilic.courier.common.service.DistanceCalculationService;
import com.asimkilic.courier.common.util.DateUtils;
import com.asimkilic.courier.courier.dto.CourierLocationDto;
import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.courier.service.CourierService;
import com.asimkilic.courier.store.config.StoreConfig;
import com.asimkilic.courier.store.dto.StoreDto;
import com.asimkilic.courier.store.service.StoreEntranceService;
import com.asimkilic.courier.store.service.StoreService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.asimkilic.courier.common.config.async.AsyncConfiguration.DEFAULT_THREAD_EXECUTOR_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationEventHandler {

    private final StoreService storeService;

    private final StoreEntranceService storeEntranceService;

    private final CourierService courierService;

    private final StoreConfig storeConfig;

    private final DistanceCalculationService calculationService;

    @Async(DEFAULT_THREAD_EXECUTOR_NAME)
    @EventListener
    @Transactional
    public void handle(LocationRequest event) {
        var nearestStore = findNearestStore(event);
        if (nearestStore == null) {
            return;
        }
        var lastEntranceTime = getLastEntranceTime(event);
        var locations = getCourierLocationDtos(event, lastEntranceTime);
        var totalDistance = getTotalDistance(locations);
        saveEntrance(event, nearestStore, totalDistance);
        log.info("Courier with id: {}, entranced to {}, distance to store is : {}m, last total trip is: {}m",
                event.courierId(), nearestStore.getStoreName(), nearestStore.getDistanceToStore(), totalDistance);
    }

    private Double getTotalDistance(List<CourierLocationDto> locations) {
        var totalDistance = 0d;
        if (locations.size() > 1) {
            for (int i = 0; i < locations.size() - 1; i++) {
                var firstLocation = locations.get(i);
                var secondLocation = locations.get(i + 1);

                var firstLat = firstLocation.latitude();
                var firstLng = firstLocation.longitude();

                var secondLat = secondLocation.latitude();
                var secondLng = secondLocation.longitude();

                var distance = calculationService.getTotalTravelDistance(firstLat, firstLng, secondLat, secondLng);
                totalDistance += distance;
            }
        }
        return totalDistance;
    }

    private List<CourierLocationDto> getCourierLocationDtos(LocationRequest event, Instant lastEntranceTime) {
        var firstPage = courierService.getLocationsGreaterThan(event.courierId(), lastEntranceTime, PageRequest.of(0, 50));

        return Stream.iterate(firstPage, Objects::nonNull,
                        page -> {
                            int nextPageNumber = page.getNumber() + 1;
                            return nextPageNumber <= page.getTotalPages()
                                    ? courierService.getLocationsGreaterThan(event.courierId(), lastEntranceTime, PageRequest.of(nextPageNumber, page.getSize()))
                                    : null;
                        })
                .flatMap(Streamable::get)
                .sorted(Comparator.comparing(CourierLocationDto::time))
                .toList();
    }

    private Instant getLastEntranceTime(LocationRequest event) {
        var lastEntranceTime = storeEntranceService.getLastEntranceTime(event.courierId());
        if (lastEntranceTime == null) {
            lastEntranceTime = DateUtils.getStartOfDayInstantAtUTC();
        }
        return lastEntranceTime;
    }


    private void saveEntrance(LocationRequest event, StoreDto nearestStore, Double totalDistance) {
        var store = storeService.getReferenceById(nearestStore.getId());
        var courier = courierService.getReferenceById(event.courierId());
        storeEntranceService.saveEntrance(store, courier, event.time(), totalDistance);
    }


    private StoreDto findNearestStore(LocationRequest event) {
        var limitTime = event.time().minusSeconds(60);
        return storeService.findNearestStoreWithinRadiusByTime(event, storeConfig.getStoreDistanceRadius(), limitTime);
    }
}
