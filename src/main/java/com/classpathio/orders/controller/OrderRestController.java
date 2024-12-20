package com.classpathio.orders.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Map;
import java.util.Set;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping(consumes = {"application/json"})
	@ResponseStatus(CREATED)
	public Order saveOrder(@RequestBody @Validated Order order) {
		//this is to set the bidirectional relationship between order and lineitem
		order.getLineItems().forEach(lineItem -> lineItem.setOrder(order));
		return this.orderService.saveOrder(order);
	}
	
	@GetMapping(produces = {"application/json"})
	@ResponseStatus(OK)
	public Map<String, Object> fetchAllOrders(
			@RequestParam(name = "page", required = false, defaultValue = "1")int page, 
			@RequestParam(name = "size", required = false, defaultValue = "10")int size, 
			@RequestParam(name = "order", required = false, defaultValue = "asc")String direction, 
			@RequestParam(name = "field", required = false, defaultValue = "customerName")String field,
			@RequestParam(name = "min", required = false, defaultValue = "true") boolean flag){
		return this.orderService.fetchAllOrders(page, size, direction, field, flag);
	}
	
	@GetMapping(value="/price", produces = {"application/json"} )
	@ResponseStatus(OK)
	public Set<Order> fetchAllOrdersByPriceRange(
			@RequestParam(name = "min", required = false, defaultValue = "500")double min, 
			@RequestParam(name = "max", required = false, defaultValue = "2000")double max){
		return this.orderService.fetchOrdersByPriceRange(min, max);
	}
	
	@GetMapping(value="/{id}", produces = {"application/json"})
	@ResponseStatus(OK)
	public Order fetchOrderById(@PathVariable long id){
		return this.orderService.fetchOrderById(id);
	}
	
	@PutMapping(value="/{id}", consumes = {"application/json"})
	public Order updateOrderById(@PathVariable long id, @RequestBody Order order) {
		return this.orderService.updateOrderById(id, order);
	}
	
	@DeleteMapping(value = "/{id}", consumes = {"application/json"})
	@ResponseStatus(NO_CONTENT)
	public void deleteOrderById(@PathVariable long id) {
		this.orderService.deleteOrderById(id);
	}
	

}
