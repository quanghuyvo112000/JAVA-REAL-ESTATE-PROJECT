package com.tdtu.finalproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Cho phép các phương thức GET, POST, PUT, DELETE, OPTIONS
                        .allowedOrigins("*") // Cho phép tất cả các nguồn gốc (bạn có thể chỉ định nguồn gốc cụ thể)
                        .allowedHeaders("*"); // Cho phép tất cả các tiêu đề
            }
        };
    }
}
