package com.applemart.product;

import com.applemart.product.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Optional<Product> findBySlug(String slug);
    List<Product> findByCategory(Category category);
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.slug LIKE :slug% ")
    List<Product> findBySlugStartsWith(@Param("slug") String slug);

    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> searchProductByName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT p.name FROM Product p WHERE p.name LIKE %:query%")
    List<String> findSuggestions(@Param("query") String query);

}
