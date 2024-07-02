package com.applestore.backend.product.productItem;


public interface ProductItemService {
    ProductItemDTO createProductItem(ProductItemDTO productItemDTO);
    void deleteProductItemById(Integer id);
}
