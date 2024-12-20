package com.classpathio.orders.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name="orders")
@Builder
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "customer name cannot be empty")
	private String customerName;
	
	@Email(message="email should be in correct format")
	@NotBlank(message = "Email field cannot be empty")
	private String email;
	
	@PastOrPresent(message = "order date cannot be in the future")
	private LocalDate orderDate;
	
	@Min(value = 500, message = "minimum order value should be 500")
	@Max(value = 20000, message = "max order value should be 20000")
	private double price;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineItem> lineItems;
	
	private Order() {}
	
	public Long getId() {
		return id;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Set<LineItem> getLineItems() {
		return lineItems;
	}
	
	//scaffolding code
	public void addLineItem(LineItem lineItem) {
		if (this.lineItems == null) {
			this.lineItems = new HashSet<>();
		}
		this.lineItems.add(lineItem);
		lineItem.setOrder(this);
	}
	
	public void removeLineItem(LineItem lineItem) {
		lineItems.remove(lineItem);
		lineItem.setOrder(null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", email=" + email + ", orderDate=" + orderDate
				+ ", price=" + price + "]";
	}
}
