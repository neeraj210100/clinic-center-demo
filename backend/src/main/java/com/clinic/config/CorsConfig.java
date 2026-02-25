package com.clinic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Support multiple origins (comma-separated)
        List<String> allowedOrigins = Arrays.asList(frontendUrl.split(","));
        List<String> trimmedOrigins = allowedOrigins.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        // Use setAllowedOriginPatterns for better compatibility with credentials
        config.setAllowedOriginPatterns(trimmedOrigins);
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setExposedHeaders(Arrays.asList("*"));
        config.setMaxAge(3600L); // Cache preflight for 1 hour

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}