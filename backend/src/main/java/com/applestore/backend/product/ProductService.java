package com.applestore.backend.product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int id);
    ProductDTO createProduct(ProductDTO productDTO);
}
