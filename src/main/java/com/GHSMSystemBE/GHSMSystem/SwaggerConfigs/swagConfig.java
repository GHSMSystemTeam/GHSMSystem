package com.GHSMSystemBE.GHSMSystem.SwaggerConfigs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swagConfig {

    @Bean
    public OpenAPI swaggerConfig()
    {


        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Gerder Healthcare System Manager Application API")
                        .description("API for GHMSystem")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")));

        return openAPI;
    }
}