package com.applemart.backend.product;

import com.applemart.backend.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    List<Product> findByCategory(Category category);
    boolean existsByName(String name);
}
