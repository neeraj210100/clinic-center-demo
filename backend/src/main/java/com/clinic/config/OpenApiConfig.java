package com.clinic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clinicCenterOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.cliniccenter.com");
        prodServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setEmail("info@cliniccenter.com");
        contact.setName("Clinic Center Support");
        contact.setUrl("https://www.cliniccenter.com");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Clinic Center API")
                .version("1.0.0")
                .contact(contact)
                .description("RESTful API for Clinic Center Management System. " +
                        "This API provides endpoints for managing appointments, leads, and patient interactions.")
                .termsOfService("https://www.cliniccenter.com/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
