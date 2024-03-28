package com.nagarro.customer_management.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.customer_management.models.Customer;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
	boolean existsByEmail(String email);
	
}