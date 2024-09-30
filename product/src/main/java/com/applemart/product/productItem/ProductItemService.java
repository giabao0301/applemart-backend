package com.applemart.product.productItem;

import com.applemart.product.response.PageResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductItemService {
    ProductItemDTO getProductItemBySlug(String slug);
    ProductItemDTO getProductItemById(Integer id);
    List<ProductItemDTO> getProductItemsByProductId(Integer id);
    ProductItemDTO createProductItem(ProductItemDTO request);
    ProductItemDTO updateProductItem(Integer id, ProductItemDTO request);
    void deleteProductItem(Integer id);
}
