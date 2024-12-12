package com.applemart.product.productItem;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    Optional<ProductItem> findBySlug(String slug);

    @Query("SELECT p FROM ProductItem p WHERE p.product.id = :productId")
    List<ProductItem> findProductItemsByProductId(@Param("productId") Integer productId);

    @Query("SELECT p FROM ProductItem p WHERE p.product.slug = :slug")
    List<ProductItem> findProductItemsByProductSlug(@Param("slug") String slug);

    @Modifying
    @Transactional
    @Query("UPDATE ProductItem p SET p.quantityInStock = :quantity WHERE p.id = :productId")
    void updateQuantity(@Param("productId") Integer productId, @Param("quantity") Integer quantity);

    boolean existsByName(String name);

    @Query("SELECT p " +
            "FROM ProductItem p " +
            "WHERE p.slug LIKE %:slug% " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<ProductItem> findProductItemBy(@Param("slug") String slug, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);
}