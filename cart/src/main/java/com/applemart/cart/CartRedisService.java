package com.applemart.cart;

import com.applemart.cart.clients.product.CartItem;
import com.applemart.cart.clients.product.ProductItemDTO;
import com.applemart.cart.redis.BaseRedisService;

import java.util.List;

public interface CartRedisService extends BaseRedisService {
    List<CartItem> getProductsFromCartByUserId(String userId);
    void addProductToCart(String userId, CartItemRequest cartItemRequest);
    void updateProductInCart(String userId, CartItemRequest item);
    void deleteProductInCart(String userId, CartItemDeletionRequest request);
    void deleteAllProductsInCart(String userId);
}
