package com.asimkilic.courier.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HaversineDistanceCalculationServiceTest {

    private HaversineDistanceCalculationService distanceCalculationService;

    @BeforeEach
    public void setUp() {
        distanceCalculationService = new HaversineDistanceCalculationService();
    }

    @Test
    void shouldReturnZero_whenSamePointsSent() {
        var lat = 41.0082;
        var lon = 28.9784;
        var distance = distanceCalculationService.getTotalTravelDistance(lat, lon, lat, lon);
        assertEquals(0.0, distance);
    }

    @Test
    void shouldReturnNonZero_whenDifferentPointSent() {
        var lat1 = 41.0082;
        var lon1 = 28.9784; // Istanbul
        var lat2 = 40.7128;
        var lon2 = -74.0060; // New York
        var expectedDistance = 8069828; // Expected distance in meters

        var distance = distanceCalculationService.getTotalTravelDistance(lat1, lon1, lat2, lon2);
        assertEquals(expectedDistance, distance, 1000.0);
    }
}