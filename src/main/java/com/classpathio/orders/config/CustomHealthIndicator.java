package com.classpathio.orders.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.classpathio.orders.dao.OrderJpaRepository;

@Component
public class CustomHealthIndicator  implements HealthIndicator {

	private OrderJpaRepository orderRepository;
	
	
	public CustomHealthIndicator(OrderJpaRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public Health health() {
		
		if(this.orderRepository.count() >= 1) {
			return Health.up().withDetail("Database", "Database is running").build();
		}
		
		return Health.down().withDetail("Database", "Database is down: ").build();
	}

}
