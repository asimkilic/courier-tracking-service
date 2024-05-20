package com.asimkilic.courier.courier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

public record LocationRequest(@NotNull @PastOrPresent Instant time,
                              @NotNull @JsonProperty("courier") @Positive Long courierId,
                              @NotNull @JsonProperty("lat") Double latitude,
                              @NotNull @JsonProperty("lng") Double longitude) {

}