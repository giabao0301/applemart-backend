package com.applemart.order.clients.cart;

import com.applemart.order.clients.product.ProductItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartClient cartClient;

    public List<ProductItemDTO> getProductItemsFromCart(String userId) {
        return cartClient.getProductItemsFromCart(userId).getData();
    }

    public void deleteProductsInCart(String userId, List<Integer> productItemIds) {
        CartItemDeletionRequest cartItemDeletionRequest = new CartItemDeletionRequest();
        cartItemDeletionRequest.setProductItemIds(productItemIds);
        cartClient.deleteProductsInCart(userId, cartItemDeletionRequest);
    }

}
