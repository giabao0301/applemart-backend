package com.applemart.order.clients.product;


import com.applemart.order.common.ApiResponse;
import com.applemart.order.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name = "productItem", url = "http://localhost:8081", configuration = FeignClientConfig.class, fallback = ProductItemClientFallback.class)
public interface ProductItemClient {

    @GetMapping("/api/v1/productItems/{id}")
    ApiResponse<ProductItemDTO> getProductItemById(@PathVariable("id") String id);
}
