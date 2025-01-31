package com.example.todo_app_backend; 
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all paths and all origins
        registry.addMapping("/**")  // All paths in your app
                .allowedOrigins("http://localhost:3000", "http://localhost:8080")  // Origins allowed
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true)  // Allow credentials (cookies, authorization headers)
                .maxAge(3600);  // Cache the pre-flight response for 1 hour (in seconds)
    }
}
