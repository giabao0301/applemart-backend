package com.applemart.product.productImage;

import com.applemart.product.productItem.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductItem(ProductItem productItem);
}
