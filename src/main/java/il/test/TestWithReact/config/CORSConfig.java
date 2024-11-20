package il.test.TestWithReact.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CORSConfig {
    @Value("#{'${cors.allow}'.split(',')}")
    private List<String> corsAllowed;

    @Bean
    public WebMvcConfigurer config() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                CorsRegistration corsRegistration = registry.addMapping("/api/**");
                for (String corsAllow : corsAllowed) {
                    corsRegistration
                        .allowedOrigins(corsAllow)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
                }
            }
        };
    }
}

