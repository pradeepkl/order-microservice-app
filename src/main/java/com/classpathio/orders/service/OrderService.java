package com.classpathio.orders.service;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.classpathio.orders.dao.OrderJpaRepository;
import com.classpathio.orders.model.LineItem;
import com.classpathio.orders.model.Order;
import com.classpathio.orders.model.OrderDTO;

@Service
public class OrderService {

	private final OrderJpaRepository repository;

	public OrderService(OrderJpaRepository repository) {
		this.repository = repository;
	}

	public Order saveOrder(Order order) {
		return this.repository.save(order);
	}

	// instead of pull and sending all the records, we have to fetch in batches
	public Map<String, Object> fetchAllOrders(int page, int size, String direction, String field, boolean flag) {

		Sort.Direction order = direction.equalsIgnoreCase("asc") ? ASC : DESC;

		PageRequest pageRequest = PageRequest.of(page, size, order, field);
		Page<OrderDTO> pageResponseDTO = null;
		Page<Order> pageResponseOrder = null;
		if (flag) {
			pageResponseDTO = this.repository.findBy(pageRequest);
		} else {
			pageResponseOrder = this.repository.findAll(pageRequest);
		}

		// construct the response and return the response
		if (flag) {
			List<OrderDTO> orders = pageResponseDTO.getContent();
			int pageNumber = pageResponseDTO.getNumber();
			int recordsPerPage = pageResponseDTO.getSize();
			long totalRecords = pageResponseDTO.getTotalElements();
			int totalPage = pageResponseDTO.getTotalPages();

			Map<String, Object> response = new LinkedHashMap<>();
			response.put("total-pages", totalPage);
			response.put("total-records", totalRecords);
			response.put("current-page", pageNumber);
			response.put("records-per-page", recordsPerPage);
			response.put("data", orders);
			return response;
		} else {
			List<Order> orders = pageResponseOrder.getContent();
			int pageNumber = pageResponseOrder.getNumber();
			int recordsPerPage = pageResponseOrder.getSize();
			long totalRecords = pageResponseOrder.getTotalElements();
			int totalPage = pageResponseOrder.getTotalPages();

			Map<String, Object> response = new LinkedHashMap<>();
			response.put("total-pages", totalPage);
			response.put("total-records", totalRecords);
			response.put("current-page", pageNumber);
			response.put("records-per-page", recordsPerPage);
			response.put("data", orders);
			return response;
		}
	}

	public Set<Order> fetchOrdersByPriceRange(double min, double max) {
		return this.repository.findByPriceBetween(min, max);
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

	// setting both sides order and line items explicitly
	private void updateLineItems(Order existingOrder, Set<LineItem> newLineItems) {
		Optional.ofNullable(newLineItems).ifPresent(lineItems -> {
			// remove the old items and add the new items
			existingOrder.getLineItems().clear();

			// add the new line items
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
