package com.applemart.product.variation;

import com.applemart.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Integer> {
    void deleteAllByCategoryId(int categoryId);
}
