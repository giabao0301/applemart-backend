package com.applemart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CartApplication {

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
