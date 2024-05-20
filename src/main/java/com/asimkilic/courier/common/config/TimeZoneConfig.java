package com.asimkilic.courier.common.config;

import com.asimkilic.courier.common.util.DateUtils;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "timezone.config.enabled", havingValue = "true", matchIfMissing = true)
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(DateUtils.ZONE_UTC));
    }
}
