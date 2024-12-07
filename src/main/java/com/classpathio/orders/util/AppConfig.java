package com.classpathio.orders.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(" Hello Spring Boot App !!");
		
	}

}
