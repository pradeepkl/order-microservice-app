package com.classpathio.orders.model;

import java.time.LocalDate;

public interface OrderDTO {
	
	String getCustomerName();
	public String getEmail();
	public LocalDate getOrderDate();
	public double getPrice();
	
	

}
