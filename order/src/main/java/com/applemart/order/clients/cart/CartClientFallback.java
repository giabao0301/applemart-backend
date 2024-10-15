package com.applemart.order.clients.cart;

import com.applemart.order.clients.product.ProductItemDTO;
import com.applemart.order.common.ApiResponse;
import com.applemart.order.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartClientFallback implements CartClient {
    @Override
    public ApiResponse<List<ProductItemDTO>> getProductItemsFromCart(String userId) {
        throw new ResourceNotFoundException("No product items found in cart");
    }

    @Override
    public String deleteProductsInCart(String userId, CartItemDeletionRequest request) {
        return "Fallback response";
    }
}
