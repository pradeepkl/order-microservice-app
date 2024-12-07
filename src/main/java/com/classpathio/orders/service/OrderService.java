package com.classpathio.orders.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.classpathio.orders.dao.OrderJpaRepository;
import com.classpathio.orders.model.LineItem;
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

	public Set<Order> fetchAllOrders() {
		return Set.copyOf(this.repository.findAll());
	}

	public Order updateOrderById(long id, Order updatedOrder) {
		return this.repository.findById(id).map(existingOrder -> {
			updateLineItems(existingOrder, updatedOrder.getLineItems());
			updateOrderFields(existingOrder, updatedOrder);
			return this.repository.save(existingOrder);
		}).orElseThrow(() -> new IllegalArgumentException("order id is invalid"));
	}

	private void updateOrderFields(Order existingOrder, Order updatedOrder) {
		Optional.ofNullable(updatedOrder.getCustomerName()).filter(name -> !name.isEmpty())
				.ifPresent(existingOrder::setCustomerName);

		Optional.ofNullable(updatedOrder.getPrice()).filter(price -> price > 0).ifPresent(existingOrder::setPrice);

		Optional.ofNullable(updatedOrder.getEmail()).filter(email -> !email.isEmpty())
				.ifPresent(existingOrder::setEmail);

	}
	//setting both sides order and line items explicitly
	private void updateLineItems(Order existingOrder, Set<LineItem> newLineItems) {
		Optional.ofNullable(newLineItems)
				.ifPresent(lineItems -> {
					//remove the old items and add the new items
					existingOrder.getLineItems().clear();
					
					//add the new line items
					lineItems.forEach(item -> {
						item.setOrder(existingOrder);
						existingOrder.getLineItems().add(item);
					});
				});
	}

	public Order fetchOrderById(long id) {
		return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalie order id"));
	}

	public void deleteOrderById(long id) {
		this.repository.deleteById(id);
	}

}
