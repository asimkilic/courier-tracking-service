package com.asimkilic.courier.courier.dto;

import java.io.Serial;
import java.io.Serializable;

public record CourierDistanceDto(double totalInKilometers,
                                 double totalInMeters) implements Serializable {

    @Serial
    private static final long serialVersionUID = -5384766154656142766L;
}
