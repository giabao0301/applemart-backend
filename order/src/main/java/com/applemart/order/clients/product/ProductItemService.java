package com.applemart.order.clients.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductItemClient productItemClient;

    public ProductItemDTO getProductItemById(Integer id) {
        return productItemClient.getProductItemById(id).getData();
    }
}
