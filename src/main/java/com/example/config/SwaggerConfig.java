package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("APIs de Gestion de Polzias de Seguros")
						.version("1.0")
						.description("Estas son las APIs usadas en el backend de Gestion de Polizas de Seguros")
						.license(new License()
									.name("Licencia Apache 2.0")
									.url("http://springdoc.org")));
	}
	
}
