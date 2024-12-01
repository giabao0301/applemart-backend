package com.applemart.auth.user.address;

import com.applemart.auth.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.id = :addressId")
    Optional<Address> findByUserIdAndAddressId(@Param("userId") Integer userId, @Param("addressId") Integer addressId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isDeliveryAddress = true")
    List<Address> findDefaultAddress(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.isDeliveryAddress = true WHERE a.id = :id")
    void setDefaultAddress(@Param("id") Integer id);
}
