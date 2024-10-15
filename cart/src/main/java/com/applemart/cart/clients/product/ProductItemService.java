package com.applemart.cart.clients.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductItemClient productItemClient;

    public ProductItemDTO getProductItemById(String id) {
        return productItemClient.getProductItemById(id).getData();
    }
}
