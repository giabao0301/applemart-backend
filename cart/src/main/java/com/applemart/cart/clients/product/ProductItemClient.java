package com.applemart.cart.clients.product;


import com.applemart.cart.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productItem", url = "http://localhost:8081", fallbackFactory = ProductItemClientFallback.class)
public interface ProductItemClient {

    @GetMapping("/api/v1/productItems/{id}")
    ApiResponse<ProductItemDTO> getProductItemById(@PathVariable("id") String id);
}

