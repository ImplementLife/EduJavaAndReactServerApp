package il.test.TestWithReact.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class CORSConfig {
    @Value("#{'${cors.allow}'.split(',')}")
    private List<String> corsAllowed;

    @Bean
    public WebMvcConfigurer config() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                for (String corsAllow : corsAllowed) {
                    log.info("corsAllowed: {}", corsAllow);
                    registry
                        .addMapping("/api/**")
                        .allowedOrigins(corsAllow.trim())
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
                }
            }
        };
    }
}

