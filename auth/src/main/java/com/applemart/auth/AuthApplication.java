package com.applemart.auth;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        info = @Info(
                title = "Auth Service REST APIs",
                description = "Auth Service REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Trinh Gia Bao",
                        email = "trinhbao135@gmail.com",
                        url = "https://github.com/giabao0301"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/giabao0301"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Auth Service Doc",
                url = "https://github.com/giabao0301"
        )
)
@SpringBootApplication(
        scanBasePackages = {
                "com.applemart.auth"
        }
)
@EnableFeignClients(
        basePackages = {
                "com.applemart.auth",
                "com.applemart.clients"
        }
)
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
