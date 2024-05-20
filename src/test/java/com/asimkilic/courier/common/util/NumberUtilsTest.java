package com.asimkilic.courier.common.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class NumberUtilsTest {

    @Test
    void testNegativeNumbers() {
        assertTrue(NumberUtils.isConvertable("-9124503100000"));
    }

    @Test
    void testPositiveNumbers() {
        assertTrue(NumberUtils.isConvertable("9124503100000"));
    }

    @Test
    void testLowNumbers() {
        long date = LocalDate.of(1970, 1, 1).atTime(1, 1).toInstant(ZoneOffset.UTC).toEpochMilli();
        assertTrue(NumberUtils.isConvertable(String.valueOf(date)));
    }

    @Test
    void testDecimalNumbers() {
        assertFalse(NumberUtils.isConvertable("9124503.10000"));
    }

}
