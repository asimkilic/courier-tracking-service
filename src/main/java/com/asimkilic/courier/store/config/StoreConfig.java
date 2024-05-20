package com.asimkilic.courier.store.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class StoreConfig {

    @Value("${store.distance.radius}")
    private Double storeDistanceRadius;
}
