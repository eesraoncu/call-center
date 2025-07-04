package com.example.callcenter1.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Call Center API", version = "1.0"),
        security = @SecurityRequirement(name = "bearerAuth") // Swagger'da default olarak her endpoint'e uygulanacak
)
@SecurityScheme(
        name = "bearerAuth", // Bu isim controller'daki @SecurityRequirement(name = "bearerAuth") ile eşleşmeli
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenAPIConfig {
}
