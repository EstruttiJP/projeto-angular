package br.com.estruttijp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API with Java 18 and Spring Boot 3")
						.version("v2")
						.description("API designed to test knowledge of Spring Boot and REST")
						.license(new License()
								.name("Apache 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0.html")));
					
	}
}
