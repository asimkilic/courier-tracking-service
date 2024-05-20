package com.asimkilic.courier.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties("swagger")
@PropertySource("classpath:swagger-default.properties")
public class SwaggerProperties {

    private String apiPath;

    private boolean corsEnabled;

    private Info info;

    private Contact contact;


    @Data
    static class Info {

        private String title;

        private String description;

        private String version;

    }

    @Data
    static class Contact {

        private String name;

        private String url;

        private String email;

    }

}