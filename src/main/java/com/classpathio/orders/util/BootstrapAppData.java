package com.classpathio.orders.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.classpathio.orders.dao.OrderJpaRepository;
import com.classpathio.orders.model.LineItem;
import com.classpathio.orders.model.Order;
import com.github.javafaker.Faker;

@Configuration
public class BootstrapAppData {

	private OrderJpaRepository orderRepository;

	private int ordersCount = 100;
	private Faker faker = new Faker();
	
	public BootstrapAppData(OrderJpaRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	// this method should be invoked at the start of the application
	// this method should be called after all the beans have been initialized
	// this method should insert a set of fake data to the repository
	public void bootstrapApplication() {

		IntStream.range(0, ordersCount).forEach(index -> {
			String name = faker.name().firstName();
			Date date = faker.date().past(7, TimeUnit.DAYS);
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			// create an order, assign the line items and finally save the order
			Order order = Order.builder().customerName(name).email(name + "@" + faker.internet().domainName())
					.orderDate(localDate).build();

			IntStream.range(0, faker.number().numberBetween(2, 4)).forEach(num -> {
				LineItem lineItem = LineItem.builder().name(faker.commerce().productName())
						.qty(faker.number().numberBetween(2, 4)).price(faker.number().randomDouble(2, 400, 500))
						.build();
				order.addLineItem(lineItem);
			});

			double totalOrderPrice = order.getLineItems().stream()
					.map(lineItem -> lineItem.getPrice() * lineItem.getQty()).reduce(Double::sum).orElse(0d);
			order.setPrice(totalOrderPrice);
			this.orderRepository.save(order);
		});
	}
}
