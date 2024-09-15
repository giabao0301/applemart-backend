package com.applemart.product.productAttribute;

import com.applemart.product.productConfiguration.ProductConfiguration;
import com.applemart.product.productConfiguration.ProductConfigurationId;
import com.applemart.product.productItem.ProductItem;
import com.applemart.product.variationOption.VariationOption;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    List<ProductAttribute> findByProductItem(ProductItem productItem);
}
