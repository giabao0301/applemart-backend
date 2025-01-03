package com.applemart.product.category;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.urlKey = :urlKey")
    Optional<Category> findByUrlKey(@Param("urlKey") String urlKey);

    boolean existsByUrlKeyAndName(String urlKey, String name);

    @Query("DELETE FROM Category c WHERE c.id = :id")
    @Modifying
    @Transactional
    void deleteCategoryById(@Param("id") Integer id);
}
