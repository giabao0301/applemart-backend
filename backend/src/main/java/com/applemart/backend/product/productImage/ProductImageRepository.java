package com.applemart.backend.product.productImage;

import com.applemart.backend.product.productItem.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductItem(ProductItem productItem);
}
