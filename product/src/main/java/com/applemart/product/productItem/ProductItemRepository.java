package com.applemart.product.productItem;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    Optional<ProductItem> findBySlug(String slug);

    @Query("SELECT p FROM ProductItem p WHERE p.product.id = :productId")
    List<ProductItem> findByProductId(@Param("productId") Integer productId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductItem p SET p.quantityInStock = :quantity WHERE p.id = :productId")
    void updateQuantity(@Param("productId") Integer productId, @Param("quantity") Integer quantity);

    boolean existsByName(String name);

    Optional<ProductItem> findByName(String name);
}