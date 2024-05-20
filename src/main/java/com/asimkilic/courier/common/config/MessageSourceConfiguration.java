package com.asimkilic.courier.common.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfiguration {

    private static final String DEFAULT_BUNDLE_PATH = "classpath:messages/messages";
    private static final String DEFAULTS_PATH = "classpath:messages/defaults";

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames(DEFAULT_BUNDLE_PATH, DEFAULTS_PATH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }
}
