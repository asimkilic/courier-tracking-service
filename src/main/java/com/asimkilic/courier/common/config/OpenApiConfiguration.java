package com.asimkilic.courier.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        SwaggerProperties.class
})
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI()
                .info(info());

    }

    private Info info() {
        return new Info()
                .contact(contact())
                .description(swaggerProperties.getInfo().getDescription())
                .title(swaggerProperties.getInfo().getTitle())
                .version(swaggerProperties.getInfo().getVersion());
    }

    private Contact contact() {
        return new Contact()
                .name(swaggerProperties.getContact().getName())
                .url(swaggerProperties.getContact().getUrl())
                .email(swaggerProperties.getContact().getEmail());
    }
}
