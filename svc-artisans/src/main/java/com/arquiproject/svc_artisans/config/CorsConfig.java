package com.arquiproject.svc_artisans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    // Ngrok url api requests
    config.addAllowedOrigin("https://f8dc-179-6-166-86.ngrok-free.app");
    config.addAllowedOrigin("http://localhost:4200"); // Angular localhost dev
    config.addAllowedOrigin("http://192.168.100.20:8081");
    config.setAllowCredentials(true);
    config.addAllowedHeader("*");
    config.addAllowedHeader("ngrok-skip-browser-warning");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
