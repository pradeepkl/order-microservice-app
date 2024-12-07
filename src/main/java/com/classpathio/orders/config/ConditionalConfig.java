package com.classpathio.orders.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {

	//@ConditionalOnProperty(name = "loadUser", prefix = "app", havingValue = "true")
	@Bean
	User user() {
		return new User();
	}

}

class User{
	
}
