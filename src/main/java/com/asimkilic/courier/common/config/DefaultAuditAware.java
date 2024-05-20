package com.asimkilic.courier.common.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
public class DefaultAuditAware implements AuditorAware<String> {

    @Value("${application.audit.user:default-user}")
    private String defaultUser;

    @Override
    public Optional<String> getCurrentAuditor() {
        // first try to get from security context holder when implemented.
        return Optional.of(defaultUser);
    }
}
