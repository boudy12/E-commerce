package com.ecom.ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTO {

	private long id;
	
	private long productId;
	
	private long ordertId;
	
	@Positive
	private int quantity;
	
	@Positive
	private BigDecimal price;
}
