package com.applemart.backend.product.variationOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Integer> {
    @Query("SELECT v FROM VariationOption v WHERE v.variation.name = :variationName AND v.value = :value")
    VariationOption findByVariationName(@Param("variationName") String variationName, @Param("value") String value);
}
