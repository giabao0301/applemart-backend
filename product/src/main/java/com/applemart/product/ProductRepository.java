package com.applemart.product;

import com.applemart.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    List<Product> findByCategory(Category category);
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.slug LIKE :slug% ")
    List<Product> findBySlugStartsWith(@Param("slug") String slug);
}
