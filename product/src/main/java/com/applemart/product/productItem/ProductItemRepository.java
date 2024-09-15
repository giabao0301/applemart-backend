package com.applemart.product.productItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    @Query("SELECT p FROM ProductItem p WHERE p.slug = :slug")
    Optional<ProductItem> findBySlug(@Param("slug") String slug);

    @Query("SELECT p FROM ProductItem p WHERE p.product.name = :productName")
    List<ProductItem> findByProductName(@Param("productName") String productName);

    boolean existsBySku(String sku);

    List<ProductItem> findBySku(String sku);
}