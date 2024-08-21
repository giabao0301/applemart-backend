package com.applemart.product.productItem;


public interface ProductItemService {
    ProductItemDTO getProductBySlug(String slug);
    ProductItemDTO createProductItem(ProductItemDTO request);
    ProductItemDTO updateProductItem(Integer id, ProductItemDTO request);
    void deleteProductItem(Integer id);
}
