package com.applemart.clients;

import com.applemart.ApiResponse;
import com.applemart.productItem.ProductItemDTO;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductClient {
    private final WebClient webClient;

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultProduct")
    public ProductItemDTO getProductItemById(String id) {
        return Objects.requireNonNull(webClient.get()
                        .uri("http://localhost:8081/api/v1/productItems/{id}", id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductItemDTO>>() {
                        })
                        .block())
                .getData();
    }

    public ProductItemDTO getDefaultProduct(String id) {
        return ProductItemDTO.builder()
                .id(-1)
                .sku("Default product item")
                .productName("Default product")
                .price(0.0)
                .quantity(1)
                .imageUrl("https://ragnorhydraulics.com/wp-content/uploads/2024/07/Default-Product-Images.png")
                .build();
    }
}
