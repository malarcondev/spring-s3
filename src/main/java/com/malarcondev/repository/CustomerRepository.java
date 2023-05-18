package com.malarcondev.repository;

import com.malarcondev.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value =
            "SELECT c.id FROM Customer c " +
            "WHERE c.id = ?1")
    boolean findCustomerById(@Param("customerId") Integer customerId);

    @Modifying
    @Query(value =
            "UPDATE Customer c " +
            "SET c.profileImageId = ?1 " +
            "WHERE c.id = ?2")
    void updateCustomerProfileImageId(@Param("profileImageId") String profileImageId,
                                      @Param("customerId") Integer customerId);

}
