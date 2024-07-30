package com.applemart.backend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.urlKey = :urlKey")
    Optional<Category> findByUrlKey(@Param("urlKey") String urlKey);

    boolean existsByUrlKeyAndName(String urlKey, String name);
}
