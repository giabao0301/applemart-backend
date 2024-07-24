package com.applemart.backend.product.variation;

import com.applemart.backend.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Integer> {
    Variation findByName(String name);
    List<Variation> findByCategory(Category category);
}
