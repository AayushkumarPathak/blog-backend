package com.amz.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Backend API Documentation") // Custom title
                        .description("This is Blog backend API developed in Java Spring Boot by Aayush Pathak") // Custom
                                                                                                                // description
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Aayush Pathak")
                                .url("https://github.com/AayushkumarPathak/blog-backend")
                                .email("aayushlpu122@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
