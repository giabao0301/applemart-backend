package com.applestore.backend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.urlKey = :urlKey")
    Category findByUrlKey(@Param("urlKey") String urlKey);

}
