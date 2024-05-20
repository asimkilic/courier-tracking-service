package com.asimkilic.courier.common.config.deserializer;

import com.asimkilic.courier.common.util.NumberUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private StdDateFormat stdDateFormat = new StdDateFormat();

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        if (NumberUtils.isConvertable(value)) {
            long timeStamp = Long.parseLong(value);
            return new Date(timeStamp);
        }
        try {
            return stdDateFormat.parse(value);
        } catch (ParseException e) {
            throw new IOException("Value cannot be parsed as numeric timestamp or ISO 8601 date: " + value, e);
        }
    }
}
