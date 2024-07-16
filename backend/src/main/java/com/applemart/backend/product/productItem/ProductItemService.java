package com.applemart.backend.product.productItem;


public interface ProductItemService {
    ProductItemDTO createProductItem(ProductItemDTO productItemDTO);
    void deleteProductItem(Integer id);
}
