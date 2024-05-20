package com.asimkilic.courier.common.config;

import com.asimkilic.courier.common.config.deserializer.CustomDateDeserializer;
import com.asimkilic.courier.common.config.deserializer.CustomInstantDeserializer;
import com.asimkilic.courier.common.config.deserializer.CustomLocalDateDeserializer;
import com.asimkilic.courier.common.config.deserializer.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer defaultMapperBuilderCustomizer() {
        return builder -> {

            builder.visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                    .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);


            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        };
    }

    @Bean
    @ConditionalOnProperty(value = "config.date-as-timestamp.enabled", havingValue = "true", matchIfMissing = true)
    public Jackson2ObjectMapperBuilderCustomizer dateCustomizer() {
        return builder -> {

            builder.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            builder.featuresToDisable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        };
    }

    @Bean
    @ConditionalOnProperty(value = "config.date-as-timestamp.enabled", havingValue = "true", matchIfMissing = true)
    public SimpleModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(Instant.class, new InstantSerializer());
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());

        javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        javaTimeModule.addDeserializer(Date.class, new CustomDateDeserializer());
        javaTimeModule.addDeserializer(Instant.class, new CustomInstantDeserializer());

        return javaTimeModule;
    }

    public static class InstantSerializer extends JsonSerializer<Instant> {

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            long timestamp = instant.toEpochMilli();
            jsonGenerator.writeNumber(timestamp);
        }
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            long timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            jsonGenerator.writeNumber(timestamp);
        }
    }

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            long timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            jsonGenerator.writeNumber(timestamp);
        }
    }
}
