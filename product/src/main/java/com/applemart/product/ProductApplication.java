package com.applemart.product;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Product Service REST APIs",
                description = "Product Service REST APIs Documentation",
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
                description = "Product Service Doc",
                url = "https://github.com/giabao0301"
        )
)
@SpringBootApplication
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
