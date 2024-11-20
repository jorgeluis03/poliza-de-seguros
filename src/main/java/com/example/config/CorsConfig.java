package com.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite el uso de credenciales (como cookies o tokens)
        config.addAllowedOriginPattern("*"); // Permite cualquier origen (o especifica uno si es necesario)
        config.addAllowedHeader("*"); // Permite cualquier encabezado
        config.addAllowedMethod("*"); // Permite todos los métodos HTTP (GET, POST, PUT, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica esta configuración a todas las rutas
        return source;
    }
}
