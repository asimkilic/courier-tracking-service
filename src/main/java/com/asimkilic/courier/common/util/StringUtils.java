package com.asimkilic.courier.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@UtilityClass
public class StringUtils {

    public static String cropAtMaxSize(String text, int maxSize) {
        try {
            if (!org.springframework.util.StringUtils.hasLength(text)) {
                return text;
            }
            return text.length() > maxSize ? text.substring(0, maxSize) : text;
        } catch (Exception e) {
            log.info("Exception occurred at cropAtMaxSize; text: {}; maxSize: {}; ", text, maxSize, e);
            return text;
        }
    }
}