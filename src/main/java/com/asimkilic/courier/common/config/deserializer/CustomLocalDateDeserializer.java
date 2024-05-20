package com.asimkilic.courier.common.config.deserializer;

import com.asimkilic.courier.common.util.NumberUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.io.IOException;
import java.io.Serial;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateDeserializer extends LocalDateDeserializer {

    @Serial
    private static final long serialVersionUID = 5935963396280568704L;

    public CustomLocalDateDeserializer() {
        super(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    protected CustomLocalDateDeserializer(CustomLocalDateDeserializer base, JsonFormat.Shape shape) {
        super(base, shape);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        if (NumberUtils.isConvertable(value)) {
            long timestamp = Long.parseLong(value);
            return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        }

        return super.deserialize(jsonParser, deserializationContext);
    }

    @Override
    protected LocalDateDeserializer withShape(JsonFormat.Shape shape) {
        return new CustomLocalDateDeserializer(this, shape);
    }
}
