package com.applemart.product.productItem;

import com.applemart.product.ProductDTO;
import com.applemart.product.common.PageResponse;

import java.util.List;

public interface ProductItemService {
    ProductItemDTO getProductItemBySlug(String slug);
    ProductItemDTO getProductItemById(Integer id);
    List<ProductItemDTO> getProductItemsByProductId(Integer id);
    List<ProductItemDTO> getProductItemsByProductSlug(String id);
    ProductItemDTO createProductItem(ProductItemDTO request);
    ProductItemDTO updateProductItem(Integer id, ProductItemDTO request);
    void deleteProductItem(Integer id);

    PageResponse<ProductItemDTO> searchProductItem(String slug, int page, int size, String sort, String dir, Double minPrice, Double maxPrice);

}
