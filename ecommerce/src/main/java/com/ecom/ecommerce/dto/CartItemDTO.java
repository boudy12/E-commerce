package com.ecom.ecommerce.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDTO {

	private long id;
	
	private long productID;
	
	@Positive
	private int quantity;
}
