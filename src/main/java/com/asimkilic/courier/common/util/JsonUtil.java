package com.asimkilic.courier.common.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class JsonUtil {

    public static String getJsonPropertyName(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                return jsonProperty.value();
            }
        } catch (NoSuchFieldException ex) {
            log.warn("No such property: {} in class: {}", fieldName, clazz.getName());
        }
        return fieldName;
    }
}
