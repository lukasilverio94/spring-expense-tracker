package com.dev.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // depois podemos usar o usuario logado via spring security
        // placeholder por eqto:
        return () -> Optional.of("system");
    }
}
