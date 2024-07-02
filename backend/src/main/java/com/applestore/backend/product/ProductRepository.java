package com.applestore.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);
    boolean existsByName(String name);
}
