package com.applemart.product.variation;

import com.applemart.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Integer> {
    Variation findByName(String name);
    List<Variation> findByCategory(Category category);
}
