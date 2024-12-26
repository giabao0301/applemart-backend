package com.applemart.order.clients.product;


import com.applemart.order.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

@Primary
@FeignClient(name = "productItem", url = "http://localhost:8081", fallback = ProductItemClientFallback.class)
public interface ProductItemClient {

    @GetMapping("/api/v1/productItems/{id}")
    ApiResponse<ProductItemDTO> getProductItemById(@PathVariable("id") Integer id);
}
