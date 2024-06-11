package com.applestore.backend.product;

import com.applestore.backend.productItem.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN FETCH p.variations WHERE p.id = :id")
    Optional<Product> findProductById(@Param("id") Integer id);

    @Query("SELECT p FROM Product p JOIN FETCH p.variations")
    List<Product> findAllProducts();

    boolean existsByName(String name);
}
