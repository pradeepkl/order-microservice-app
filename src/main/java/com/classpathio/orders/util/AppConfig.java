package com.classpathio.orders.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements CommandLineRunner{
	
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(" Hello Spring Boot App !!");
		
		String [] beans = this.applicationContext.getBeanDefinitionNames();
		
		System.out.println("---------------------------------");
		for(String bean: beans) {
			
			System.out.println(bean);
		}
		System.out.println("---------------------------------");
		
	}

}
