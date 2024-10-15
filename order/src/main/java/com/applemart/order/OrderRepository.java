package com.applemart.order;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer id);
    List<Order> findByUserId(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.paymentStatus = :paymentStatus WHERE o.id = :id")
    void updateOrderStatusById(@Param("id") String id, @Param("paymentStatus") String paymentStatus);
}
