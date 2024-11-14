package com.arquiproject.svc_artisans.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Local Artisans svc-artisans API")
            .version("1.0")
            .description("Documentación de la API para el microservicio de Local Artisans que contiene la lógica de usuarios, pagos y órdenes.")
        );
  }
}
