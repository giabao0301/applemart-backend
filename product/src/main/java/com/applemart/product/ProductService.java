package com.applemart.product;

import com.applemart.product.common.PageResponse;

import java.util.List;

public interface ProductService {
    PageResponse<ProductDTO> getAllProducts(int page, int size, String sort, String dir);
    ProductDTO getProductById(Integer id);
    ProductDTO getProductBySlug(String slug);
    List<ProductDTO> getProductByCategory(String category);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
}
