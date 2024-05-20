package com.asimkilic.courier.courier.controller;

import com.asimkilic.courier.courier.dto.CourierDistanceDto;
import com.asimkilic.courier.courier.dto.CourierDto;
import com.asimkilic.courier.courier.dto.CourierRequest;
import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.courier.service.CourierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/couriers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Courier")
public class CourierController {

    private final CourierService courierService;


    @GetMapping
    @Operation(summary = "Returns all couriers with pagination")
    public Page<CourierDto> getCouriers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "30") int size) {
        return courierService.getCouriers(page, size);
    }

    @PostMapping
    @Operation(summary = "Save or update a courier")
    public ResponseEntity<Void> saveCourier(@RequestBody @Valid CourierRequest courierRequest) {
        courierService.saveOrUpdateCourier(courierRequest);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/location")
    @Operation(summary = "Update courier location",
              description = "Updates the location of the courier. If the courier is within 100 meters of any store, it records this information.")
    public ResponseEntity<Void> updateLocation(@Valid @RequestBody LocationRequest locationRequest) {
        courierService.updateLocation(locationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total-distances/{courier-id}")
    @Operation(summary = "Get total distance traveled by courier",
            description = "Retrieves the total distance traveled by the courier in both kilometers (km) and meters (m).")
    public CourierDistanceDto totalDistance(@PathVariable("courier-id") Long courierId) {
        return courierService.getTotalDistance(courierId);
    }

}
