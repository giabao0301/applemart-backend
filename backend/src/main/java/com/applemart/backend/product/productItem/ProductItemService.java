package com.applemart.backend.product.productItem;


import java.util.List;

public interface ProductItemService {
    ProductItemDTO getProductBySlug(String slug);
    List<ProductItemDTO> getProductItemByProductName(String productName);
    ProductItemDTO createProductItem(ProductItemDTO request);
    ProductItemDTO updateProductItem(Integer id, ProductItemDTO request);
    void deleteProductItem(Integer id);
}
