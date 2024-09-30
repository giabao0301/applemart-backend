package com.applemart.product;

import com.applemart.product.response.PageResponse;

public interface ProductService {
    PageResponse<ProductDTO> getAllProducts(int page, int size, String sort, String dir);
    ProductDTO getProductById(Integer id);
    ProductDTO getProductBySlug(String Name);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
}
