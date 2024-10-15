package com.applemart.cart;

import com.applemart.cart.clients.product.ProductItemDTO;
import com.applemart.cart.redis.BaseRedisService;

import java.util.List;

public interface CartRedisService extends BaseRedisService {
    List<ProductItemDTO> getProductsFromCartByUserId(String userId);
    void addProductToCart(String userId, CartItemRequest cartItemRequest);
    void updateProductInCart(String userId, CartItemRequest item);
    void deleteProductsInCart(String userId, CartItemDeletionRequest request);
    void deleteAllProductsInCart(String userId);
}
