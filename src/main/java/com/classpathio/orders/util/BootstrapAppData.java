package com.classpathio.orders.util;

import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.classpathio.orders.dao.OrderJpaRepository;

@Configuration
public class BootstrapAppData {
	
	private OrderJpaRepository orderRepository;
	
	private int ordersCount = 10;
	
	
	@EventListener(ApplicationReadyEvent.class)
	//this method should be invoked at the start of the application
	//this method should be called after all the beans have been initialized
	//this method should insert a set of fake data to the repository
	public void bootstrapApplication() {
		
		IntStream.range(0, ordersCount).forEach(index -> {
			
			//create an order, assign the line items and finally save the order
			//Order order = 
		});
		
		
	}

}
