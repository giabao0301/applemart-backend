package com.applemart.backend.product.productConfiguration;

import com.applemart.backend.product.productItem.ProductItem;
import com.applemart.backend.product.variationOption.VariationOption;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductConfigurationRepository extends JpaRepository<ProductConfiguration, ProductConfigurationId> {
    List<ProductConfiguration> findByProductItem(ProductItem productItem);

    boolean existsByProductItemAndVariationOption(ProductItem productItem, VariationOption variationOption);

    @Modifying
    @Transactional
    @Query("UPDATE ProductConfiguration p SET p.variationOption = :variationOption WHERE p.id = :id")
    void updateProductConfigurationById(ProductConfigurationId id, VariationOption variationOption);
}
