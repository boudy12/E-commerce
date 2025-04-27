package com.ecom.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ecom.ecommerce.model.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {

	private long id;
	
	private long userId;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;
	
	@NotNull(message = "Order status is required")
	private Order.OrderStatus status;
	
	private LocalDateTime createdAt;
	
	private List<OrderItemDTO> orderItems;
}
