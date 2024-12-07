package com.classpathio.orders.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.classpathio.orders.dao.OrderJpaRepository;
import com.classpathio.orders.model.Order;

@Service
public class OrderService {
	
	private final OrderJpaRepository repository;
	
	public OrderService(OrderJpaRepository repository) {
		this.repository = repository;
	}
	
	public Order saveOrder(Order order) {
		return this.repository.save(order);
	}
	
	public Set<Order> fetchAllOrders(){
		return Set.copyOf(this.repository.findAll());
	}
	
	public Order updateOrderById(long id, Order updatedOrder) {
		this.repository.findById(id).ifPresent((order) -> {
			order.setCustomerName(updatedOrder.getCustomerName());
			order.setEmail(updatedOrder.getEmail());
			order.setPrice(updatedOrder.getPrice());
			order.setOrderDate(updatedOrder.getOrderDate());
			this.repository.save(order);
		});
		return updatedOrder;
	}
	
	public Order fetchOrderById(long id){
		return this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("invalie order id"));
	}
	
	public void deleteOrderById(long id) {
		this.repository.deleteById(id);
	}

}
