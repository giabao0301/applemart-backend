package com.applemart.order.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    Optional<ShippingMethod> findByName(String name);
}
