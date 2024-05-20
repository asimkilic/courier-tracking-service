package com.asimkilic.courier.common.config.deserializer;

import com.asimkilic.courier.common.util.NumberUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;

public class CustomInstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        if (NumberUtils.isConvertable(value)) {
            long timestamp = Long.parseLong(value);
            return Instant.ofEpochMilli(timestamp);
        }
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            throw new IOException("Value cannot be parsed as a numeric timestamp or ISO 8601 Instant: " + value, e);
        }
    }
}
