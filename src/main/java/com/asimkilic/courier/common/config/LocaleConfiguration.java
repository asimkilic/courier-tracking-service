package com.asimkilic.courier.common.config;


import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

@Configuration
public class LocaleConfiguration {

    @Value("${locale.default:tr-TR}")
    private String defaultLocale;

    @Bean
    @ConditionalOnWebApplication(type = REACTIVE)
    public LocaleContextResolver localeContextResolver() {
        AcceptHeaderLocaleContextResolver acceptHeaderLocaleContextResolver = new AcceptHeaderLocaleContextResolver();
        acceptHeaderLocaleContextResolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));
        return acceptHeaderLocaleContextResolver;
    }

    @Bean
    @ConditionalOnWebApplication(type = SERVLET)
    public LocaleResolver localeResolver() {
        org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver localeResolver = new org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(
                Locale.forLanguageTag(defaultLocale));
        return localeResolver;
    }
}
