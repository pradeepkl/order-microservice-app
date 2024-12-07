package com.classpathio.orders.controller;

import java.util.Set;

import com.classpathio.orders.model.Order;
import com.classpathio.orders.service.OrderService;

public class OrderRestController {
	
	private final OrderService orderService;
	
	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public Order saveOrder(Order order) {
		return this.orderService.saveOrder(order);
	}
	
	public Set<Order> fetchAllOrders(){
		return this.orderService.fetchAllOrders();
	}
	
	public Order updateOrderById(long id, Order order) {
		return this.orderService.updateOrderById(id, order);
	}
	
	public void deleteOrderById(long id) {
		this.orderService.deleteOrderById(id);
	}
	

}
