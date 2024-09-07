package com.applemart.product.productItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    @Query("SELECT p FROM ProductItem p WHERE p.slug LIKE :slug% ")
    List<ProductItem> findBySlugStartsWith(@Param("slug") String slug);

    boolean existsBySku(String sku);

    List<ProductItem> findBySku(String sku);
}