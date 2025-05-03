package com.copilot.assignment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Configuration class for setting up OpenAPI documentation with Swagger.
 * This class defines the OpenAPI bean and configures security schemes.
 */
@Configuration
public class OpenAPIConfiguration {
    private static final String BEARER_TOKEN = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Value("${server.servlet.context-path}")
    public String contextPath;

    /**
     * Creates and configures the OpenAPI bean.
     * Adds server information and security requirements for the API.
     *
     * @return the configured OpenAPI instance
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .openapi("3.0.3")
                .addServersItem(new Server().url(contextPath))
                .components(new Components()
                        // define the bearer SecurityScheme
                        .addSecuritySchemes(AUTHORIZATION_HEADER, bearerTokenSecurityScheme()))
                // then apply it. If you don't apply it will not be added to the header in cURL
                .security(Collections.singletonList(new SecurityRequirement().addList(AUTHORIZATION_HEADER)));
    }

    /**
     * Defines the security scheme for bearer token authentication.
     *
     * @return the configured SecurityScheme instance
     */
    public SecurityScheme bearerTokenSecurityScheme() {
        return new SecurityScheme()
                .scheme(BEARER_TOKEN)
                .name(AUTHORIZATION_HEADER) // authorisation-token
                .description("Authorization Header Required only for the Private APIs")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP);
    }
}
