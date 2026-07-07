package com.example.rest_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI todoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo API")
                        .description("A comprehensive Todo Management API built with Spring Boot")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Fazil Husen")
                                .email("fazil.husen6@gmail.com")
                                .url("https://github.com/FazilKlkt"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://fazilhusen.com")
                                .description("Production Server")
                ));
    }
}
