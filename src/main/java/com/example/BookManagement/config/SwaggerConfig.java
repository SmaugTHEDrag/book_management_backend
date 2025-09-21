package com.example.BookManagement.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class for Swagger / OpenAPI documentation.
 * Also defines JWT bearer authentication for secured endpoints.
 */
@Configuration
@SecurityScheme(
        name = "bearerAuth",            // Name of the security scheme, referenced in OpenAPI
        scheme = "bearer",              // Authentication scheme type
        type = SecuritySchemeType.HTTP, // HTTP authentication type
        in = SecuritySchemeIn.HEADER,  // Token passed in HTTP header
        bearerFormat = "JWT"           // JWT format for bearer token
)
public class SwaggerConfig {
    /*
     * Configure the main OpenAPI bean.
     * Sets basic API info and global security requirement.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                // General API information
                .info(new Info()
                        .title("Book Management API")   // API title
                        .version("1.0.0")  // Version
                        .description("API documentation for Book Management project"))   // Description
                // Global security requirement for all endpoints
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
