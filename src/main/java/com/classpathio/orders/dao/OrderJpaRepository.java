package com.classpathio.orders.dao;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classpathio.orders.model.Order;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long>{
	
	Set<Order> findByPriceBetween(double min, double max);
	
	Set<Order> findByOrderDateAfter(LocalDate startDate);
	
	Set<Order> findByCustomerName(String customerName);
	
	Optional<Order> findByEmail(String email);

}
