package com.clinic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${frontend.url}")
    private String url;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(url, "http://localhost:*", "https://*","https://incandescent-pasca-b52ff6.netlify.app"  // Explicit production URL
                ) // Use patterns for flexibility
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // Removed allowCredentials(true) - this conflicts with wildcard patterns
                // Patterns work properly without credentials requirement
                .maxAge(3600);
    }
}