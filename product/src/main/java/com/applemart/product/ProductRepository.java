package com.applemart.product;

import com.applemart.product.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Optional<Product> findBySlug(String slug);
    List<Product> findByCategory(Category category);
    boolean existsByName(String name);
}
