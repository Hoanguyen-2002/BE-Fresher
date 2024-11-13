package com.lg.fresher.lgcommerce.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Fresher",
                        email = "pleasehelme@gmail.com"
                ),
                title = "OpenApi specification - LG Ecommerce",
                description = "OpenApi documentation for Fresher Training Project",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:6969"
                )
        }

)
public class OpenApiConfig {
}
