package com.asimkilic.courier.common.util;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class NumberUtils {

    public static final Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+");

    public static boolean isConvertable(String source) {
        return StringUtils.isNotBlank(source) && NUMBER_PATTERN.matcher(source).matches();
    }
}
