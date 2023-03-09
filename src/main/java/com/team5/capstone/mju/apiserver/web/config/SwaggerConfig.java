package com.team5.capstone.mju.apiserver.web.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/v1/**", "/"};

        return GroupedOpenApi.builder()
                .group("api-server-docs")
                .pathsToMatch(paths)
                .build();
    }
}
