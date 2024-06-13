package com.applestore.backend.variation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer> {
    ProductVariation findByName(String name);
}
