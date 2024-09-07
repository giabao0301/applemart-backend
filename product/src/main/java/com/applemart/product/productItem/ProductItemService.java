package com.applemart.product.productItem;

import com.applemart.product.response.PageResponse;

import java.util.List;

public interface ProductItemService {
    List<ProductItemDTO> getProductItemsBySlug(String slug);
    ProductItemDTO createProductItem(ProductItemDTO request);
    ProductItemDTO updateProductItem(Integer id, ProductItemDTO request);
    void deleteProductItem(Integer id);
}
