package com.applestore.backend.variation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, Integer> {
    Variation findByName(String name);
}
