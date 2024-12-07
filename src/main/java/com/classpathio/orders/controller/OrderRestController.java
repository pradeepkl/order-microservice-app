package com.classpathio.orders.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(CREATED)
	public Order saveOrder(@RequestBody Order order) {
		//this is to set the bidirectional relationship between order and lineitem
		order.getLineItems().forEach(lineItem -> lineItem.setOrder(order));
		return this.orderService.saveOrder(order);
	}
	
	@GetMapping
	@ResponseStatus(OK)
	public Set<Order> fetchAllOrders(){
		return this.orderService.fetchAllOrders();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(OK)
	public Order fetchOrderById(@PathVariable long id){
		return this.orderService.fetchOrderById(id);
	}
	
	@PutMapping("/{id}")
	public Order updateOrderById(@PathVariable long id, @RequestBody Order order) {
		return this.orderService.updateOrderById(id, order);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void deleteOrderById(@PathVariable long id) {
		this.orderService.deleteOrderById(id);
	}
	

}
