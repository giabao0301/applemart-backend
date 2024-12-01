package com.applemart.order.clients.cart;

import com.applemart.order.common.ApiResponse;
import com.applemart.order.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Primary
@FeignClient(name = "cart", url = "http://localhost:8082", configuration = FeignClientConfig.class, fallback = CartClientFallback.class)
public interface CartClient {
    @GetMapping("/api/v1/carts/{userId}")
    ApiResponse<List<CartItem>> getProductItemsFromCart(@PathVariable("userId") String userId);

    @DeleteMapping("/api/v1/carts/{userId}")
    String deleteProductsInCart(@PathVariable("userId") String userId, @RequestBody CartItemDeletionRequest request);
}
