package Employee.demo;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // If not using Spring Boot's default configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/api/**") // Specify the URL pattern you want to allow CORS for
                .allowedOrigins("http://localhost:5173", "http://localhost:5174","http://109.186.13.25:5173") // Specify the allowed origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the allowed HTTP methods
                .allowedHeaders("*")
                .allowCredentials(true); // Allow sending cookies from the client
        //
    }
}
