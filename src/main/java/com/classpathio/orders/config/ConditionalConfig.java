package com.classpathio.orders.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {

	@ConditionalOnProperty(name = "loadUser", prefix = "app", havingValue = "false", matchIfMissing = false)
	@Bean
	User userBasedOnPropetyCondition() {
		return new User();
	}
	
	@ConditionalOnClass(name = "com.classpathio.orders.util.AppConfig")
	@Bean
	User userBasedonClass() {
		return new User();
	}
	
	@ConditionalOnBean(name = "userBasedOnPropetyCondition")
	@Bean
	User userBasedOnBean() {
		return new User();
	} 
	
	@ConditionalOnMissingBean(name = "userBasedOnPropetyCondition")
	@Bean
	User userBasedOnMissingBean() {
		return new User();
	}

}

class User{
	
}
