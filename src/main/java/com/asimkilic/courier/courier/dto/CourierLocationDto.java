package com.asimkilic.courier.courier.dto;

import java.time.Instant;

public record CourierLocationDto(Instant time,
                                 Double latitude,
                                 Double longitude) {

}
