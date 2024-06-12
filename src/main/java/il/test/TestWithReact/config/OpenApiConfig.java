package il.test.TestWithReact.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "REST API documentation",
        version = "1.0",
        description = """
            Test with react api
            """,
        contact = @Contact(name = "Implement Life", email = "implementlifep@gmail.com")
    )
)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
            .group("REST API")
            .pathsToMatch("/**")
            .build();
    }
}
