package com.classpathio.orders.controller;

import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classpathio.orders.model.Order;
import com.classpathio.orders.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
	
	private final OrderService orderService;
	
	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public Order saveOrder(@RequestBody Order order) {
		return this.orderService.saveOrder(order);
	}
	
	@GetMapping
	public Set<Order> fetchAllOrders(){
		return this.orderService.fetchAllOrders();
	}
	
	@GetMapping
	public Order fetchOrderById(@PathVariable long id){
		return this.orderService.fetchOrderById(id);
	}
	
	@PutMapping("/{id}")
	public Order updateOrderById(@PathVariable long id, @RequestBody Order order) {
		return this.orderService.updateOrderById(id, order);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrderById(@PathVariable long id) {
		this.orderService.deleteOrderById(id);
	}
	

}
