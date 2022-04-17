package com.zaerald.fxratesapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(apiInfo())
            .externalDocs(externalDocumentation());
    }

    private Info apiInfo() {
        return new Info()
            .title("FX Rates API")
            .description("FX Rate API Spring Boot Project")
            .version("0.0.1-SNAPSHOT")
            .license(license());
    }

    private License license() {
        return new License()
            .name("MIT")
            .url("https://github.com/zaerald/fx-rates-api/blob/main/LICENSE");
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
            .description("README")
            .url("https://github.com/zaerald/fx-rates-api/blob/main/README.md");
    }

}
