package com.asimkilic.courier.common.util;

import com.asimkilic.courier.common.annotation.Crop;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class FieldUtils {

    public static void cropValueWithAnnotation(Object entity) {
        org.apache.commons.lang3.reflect.FieldUtils.getFieldsListWithAnnotation(entity.getClass(), Crop.class)
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        int maxSize = field.getAnnotation(Crop.class).value();
                        Object fieldValue = field.get(entity);
                        if (Objects.isNull(fieldValue)) {
                            return;
                        }
                        field.set(entity, StringUtils.cropAtMaxSize(fieldValue.toString(), maxSize));
                    } catch (IllegalAccessException e) {
                        log.info("Exception occurred while cropping field: {}; ", field.getName(), e);
                    }
                });
    }
}
