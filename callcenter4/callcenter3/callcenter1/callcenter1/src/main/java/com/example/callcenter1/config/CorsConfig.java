package com.example.callcenter1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Uygulamanın CORS (Cross-Origin Resource Sharing) ayarlarını yöneten konfigürasyon sınıfıdır.
 * Burada tanımlanan ayarlar sayesinde, frontend uygulaman farklı bir port veya domainde çalışsa bile backend'e erişebilir.
 * NOT: allowCredentials(true) ile birlikte allowedOrigins('*') kullanılamaz. Frontend adresini açıkça belirtmelisin.
 */
@Configuration
public class CorsConfig {
    /**
     * Tüm endpointler için temel CORS ayarlarını uygular.
     * allowedOrigins kısmına frontend adresini açıkça yazmalısın.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@org.springframework.lang.NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173","http://localhost:5174") // Frontend adresini açıkça yaz
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
