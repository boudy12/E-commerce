package com.ecom.ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne
	@JoinColumn(name = "product_id",nullable = false)
	private Product product;
	
	private int quantity;
	
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "order_id",nullable = false)
	private Order order;
	
	public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
	    this.order = order;
	    this.product = product;
	    this.quantity = quantity;
	    this.price = price;
	}

}

