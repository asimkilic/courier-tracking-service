package com.asimkilic.courier.store.handler;

import com.asimkilic.courier.common.service.DistanceCalculationService;
import com.asimkilic.courier.common.util.DateUtils;
import com.asimkilic.courier.courier.dto.CourierLocationDto;
import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.courier.entity.Courier;
import com.asimkilic.courier.courier.service.CourierService;
import com.asimkilic.courier.store.config.StoreConfig;
import com.asimkilic.courier.store.dto.StoreDto;
import com.asimkilic.courier.store.entity.Store;
import com.asimkilic.courier.store.service.StoreEntranceService;
import com.asimkilic.courier.store.service.StoreService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationEventHandlerTest {

    @InjectMocks
    private LocationEventHandler locationEventHandler;

    @Mock
    private StoreService storeService;

    @Mock
    private StoreEntranceService storeEntranceService;

    @Mock
    private CourierService courierService;

    @Mock
    private StoreConfig storeConfig;

    @Mock
    private DistanceCalculationService calculationService;

    @Test
    void shouldDoNothing_whenCourierNotNearAnyStore() {
        //given
        var time = DateUtils.getCurrentInstant();
        var limitTime = time.minusSeconds(60);
        var request = new LocationRequest(time, 1L, 1.0d, 1.0d);
        var radius = 100d;

        when(storeConfig.getStoreDistanceRadius()).thenReturn(radius);
        when(storeService.findNearestStoreWithinRadiusByTime(request, radius, limitTime)).thenReturn(null);

        //when
        locationEventHandler.handle(request);

        //then
        verify(storeConfig, times(1)).getStoreDistanceRadius();
        verify(storeService, times(1)).findNearestStoreWithinRadiusByTime(request, radius, limitTime);
        verify(storeEntranceService, never()).getLastEntranceTime(request.courierId());
    }

    @Test
    void shouldSaveEntranceWhenNearestStoreIsFound() {
        // given
        var time = DateUtils.getCurrentInstant();
        var radius = 100d;
        var limitTime = time.minusSeconds(60);
        var lastEntranceTime = time.minusSeconds(600);
        var locationRequest = new LocationRequest(time, 1L, 1.0d, 1.0d);
        var firstCourierLocation = new CourierLocationDto(Instant.now().minusSeconds(300), 40.0, 29.0);
        var secondCourierLocation = new CourierLocationDto(Instant.now().minusSeconds(100), 40.0, 29.0);
        var firstPageResponse = new PageImpl<>(List.of(firstCourierLocation), PageRequest.of(0, 50), 2);
        var secondPageResponse = new PageImpl<>(List.of(secondCourierLocation), PageRequest.of(1, 50), 2);
        var totalDistance = 10d;
        var courierReference = Courier.builder().id(locationRequest.courierId()).build();
        var nearestStore = new StoreDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public Double getDistanceToStore() {
                return 1210.5434;
            }

            @Override
            public String getStoreName() {
                return "MM Migros";
            }
        };
        var storeReference = Store.builder().id(nearestStore.getId()).build();

        when(storeConfig.getStoreDistanceRadius()).thenReturn(radius);
        when(storeService.findNearestStoreWithinRadiusByTime(locationRequest, radius, limitTime)).thenReturn(nearestStore);
        when(storeEntranceService.getLastEntranceTime(locationRequest.courierId())).thenReturn(lastEntranceTime);
        when(courierService.getLocationsGreaterThan(locationRequest.courierId(), lastEntranceTime, PageRequest.of(0, 50))).thenReturn(firstPageResponse);
        when(courierService.getLocationsGreaterThan(locationRequest.courierId(), lastEntranceTime, PageRequest.of(1, 50))).thenReturn(secondPageResponse);
        when(calculationService.getTotalTravelDistance(40.0, 29.0, 40.0, 29.0)).thenReturn(totalDistance);
        when(storeService.getReferenceById(nearestStore.getId())).thenReturn(storeReference);
        when(courierService.getReferenceById(locationRequest.courierId())).thenReturn(courierReference);

        //when
        locationEventHandler.handle(locationRequest);

        //then
        verify(storeEntranceService, times(1)).saveEntrance(storeReference, courierReference, locationRequest.time(), totalDistance);
        verify(storeConfig, times(1)).getStoreDistanceRadius();
        verify(storeService, times(1)).findNearestStoreWithinRadiusByTime(locationRequest, radius, limitTime);
        verify(storeEntranceService, times(1)).getLastEntranceTime(locationRequest.courierId());
        verify(courierService, times(1)).getLocationsGreaterThan(locationRequest.courierId(), lastEntranceTime, PageRequest.of(0, 50));
        verify(courierService, times(1)).getLocationsGreaterThan(locationRequest.courierId(), lastEntranceTime, PageRequest.of(1, 50));
        verify(calculationService, times(1)).getTotalTravelDistance(40.0, 29.0, 40.0, 29.0);
        verify(storeService, times(1)).getReferenceById(nearestStore.getId());
        verify(courierService, times(1)).getReferenceById(locationRequest.courierId());
    }


}