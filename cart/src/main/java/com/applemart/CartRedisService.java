package com.applemart;

import com.applemart.productItem.ProductItemDTO;
import com.applemart.redis.BaseRedisService;

import java.util.List;

public interface CartRedisService extends BaseRedisService {
    List<ProductItemDTO> getProductsFromCartByUserId(String userId);
    void addProductToCart(String userId, CartItemRequest cartItemRequest);
    void updateProductInCart(String userId, CartItemRequest item);
    void deleteProductsInCart(String userId, CartItemDeletionRequest request);
    void deleteAllProductsInCart(String userId);
}
