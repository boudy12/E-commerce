package com.ecom.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
    @ManyToOne
    @JoinColumn(name = "user_id", nullable =  false)
	private User user;
	
	private String address;
	
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private LocalDateTime createdAt;
	
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();
	
	public enum OrderStatus{
		PREPARING, DELIVERING, DELIVERED, CANCELED
	}
}
