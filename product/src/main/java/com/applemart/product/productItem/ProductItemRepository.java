package com.applemart.product.productItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    Optional<ProductItem> findBySlug(String slug);

    @Query("SELECT p FROM ProductItem p WHERE p.product.id = :productId")
    List<ProductItem> findByProductId(@Param("productId") Integer productId);

    boolean existsByName(String name);

    Optional<ProductItem> findByName(String name);
}