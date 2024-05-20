package com.asimkilic.courier.courier.service;


import com.asimkilic.courier.common.annotation.EnableDefaultFilter;
import com.asimkilic.courier.courier.dto.CourierDistanceDto;
import com.asimkilic.courier.courier.dto.CourierDto;
import com.asimkilic.courier.courier.dto.CourierLocationDto;
import com.asimkilic.courier.courier.dto.CourierRequest;
import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.courier.entity.Courier;
import com.asimkilic.courier.courier.mapper.CourierMapper;
import com.asimkilic.courier.courier.repository.CourierLocationRepository;
import com.asimkilic.courier.courier.repository.CourierRepository;
import com.asimkilic.courier.store.service.StoreEntranceService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {

    private final CourierMapper courierMapper;

    private final CourierRepository courierRepository;

    private final CourierLocationRepository courierLocationRepository;

    private final StoreEntranceService storeEntranceService;

    private final ApplicationEventPublisher publisher;


    @Transactional
    public void saveOrUpdateCourier(CourierRequest courierRequest) {
        var optionalCourier = courierRepository.findCourierByIdentityNumber(courierRequest.identityNumber());
        optionalCourier.ifPresentOrElse(existingCourier ->
                        existingCourier.setFullName(courierRequest.fullName()),
                () -> {
                    var entity = courierMapper.convert(courierRequest);
                    courierRepository.save(entity);
                    log.info("Courier {}-{} saved", courierRequest.fullName(), courierRequest.identityNumber());
                });
    }

    @Transactional
    public void updateLocation(LocationRequest locationRequest) {
        var isCourierActive = isCourierActiveById(locationRequest.courierId());
        if (!isCourierActive) {
            log.info("Courier with id {} not found", locationRequest.courierId());
            return;
        }
        var courierReference = courierRepository.getReferenceById(locationRequest.courierId());
        var courierLocation = courierMapper.convert(locationRequest, courierReference);
        courierLocationRepository.save(courierLocation);
        publisher.publishEvent(locationRequest);
    }

    @EnableDefaultFilter(filterNames = "activeFilter")
    @Transactional(readOnly = true)
    public Page<CourierDto> getCouriers(int page, int size) {
        return courierRepository.getCouriers(PageRequest.of(page, size));
    }

    public CourierDistanceDto getTotalDistance(Long courierId) {
        double totalDistanceInMeters = storeEntranceService.getTotalTravelDistanceByCourierId(courierId);
        return new CourierDistanceDto(totalDistanceInMeters / 1000, totalDistanceInMeters);
    }

    public Courier getReferenceById(Long courierId) {
        return courierRepository.getReferenceById(courierId);
    }

    @Transactional(readOnly = true)
    public Page<CourierLocationDto> getLocationsGreaterThan(Long courierId, Instant lastEntranceTime, PageRequest pageRequest) {
        return courierRepository.getLocationsGreaterThan(courierId, lastEntranceTime, pageRequest);
    }

    private boolean isCourierActiveById(Long id) {
        return courierRepository.isCourierActiveByCourierId(id);
    }
}
