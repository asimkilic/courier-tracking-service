package com.asimkilic.courier.common.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DateUtils {

    public static final ZoneId ZONE_TR = ZoneId.of("Turkey");
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    private static final Clock CLOCK = Clock.systemUTC();

    public static Instant getCurrentInstant() {
        return Instant.now(CLOCK);
    }

    public static Instant getStartOfDayInstantAtUTC() {
        LocalDate today = LocalDate.now();
        ZonedDateTime startOfDay = today.atStartOfDay(ZONE_UTC);
        return startOfDay.toInstant();
    }


    public static String formatInstantAtUTC(Instant instant, String pattern) {
        return DateTimeFormatter
                .ofPattern(pattern)
                .withZone(ZONE_UTC)
                .format(instant);
    }

    public static String formatInstantAtTR(Instant instant, String pattern) {
        return DateTimeFormatter
                .ofPattern(pattern)
                .withZone(ZONE_TR)
                .format(instant);
    }
}
