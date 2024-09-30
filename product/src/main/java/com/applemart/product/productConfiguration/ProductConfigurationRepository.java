package com.applemart.product.productConfiguration;

import com.applemart.product.productItem.ProductItem;
import com.applemart.product.variationOption.VariationOption;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductConfigurationRepository extends JpaRepository<ProductConfiguration, ProductConfigurationId> {
    List<ProductConfiguration> findByProductItem(ProductItem productItem);

    boolean existsByProductItemAndVariationOption(ProductItem productItem, VariationOption variationOption);

    @Modifying
    @Transactional
    @Query("UPDATE ProductConfiguration p SET p.variationOption = :variationOption WHERE p.id = :id")
    void updateProductConfigurationById(@Param("id") ProductConfigurationId id, @Param("variationOption") VariationOption variationOption);
}
