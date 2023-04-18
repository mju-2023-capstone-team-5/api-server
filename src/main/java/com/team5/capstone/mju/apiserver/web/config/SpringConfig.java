package com.team5.capstone.mju.apiserver.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("local")
@OpenAPIDefinition(
        info = @Info(
                title = "SOPAR Rest API 명세서",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "Our Team",
                        email = "jaehan1346@gmail.com",
                        url = "https://github.com/mju-2023-capstone-team-5"
                )
        )
)
public class SpringConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
