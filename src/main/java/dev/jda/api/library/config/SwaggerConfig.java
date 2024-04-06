package dev.jda.api.library.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final ApiConfiguration apiConfiguration;

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info().title(apiConfiguration.getTitle())
                .description(apiConfiguration.getDescription())
                .version(apiConfiguration.getVersion());
    }
}
