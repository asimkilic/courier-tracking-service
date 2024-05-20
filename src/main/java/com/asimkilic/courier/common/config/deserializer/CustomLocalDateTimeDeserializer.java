package com.asimkilic.courier.common.config.deserializer;

import com.asimkilic.courier.common.util.NumberUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.IOException;
import java.io.Serial;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    @Serial
    private static final long serialVersionUID = 798584093242443122L;


    public CustomLocalDateTimeDeserializer() {
        super(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getText();
        if (NumberUtils.isConvertable(value)) {
            long timestamp = Long.parseLong(value);
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }

        return super.deserialize(parser, context);
    }
}
