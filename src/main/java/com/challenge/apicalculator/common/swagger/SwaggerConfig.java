package com.challenge.apicalculator.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Calculator")
                        .version("1.0")
                        .description("API para realizar cálculos y consultar el historial de llamadas")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
} 