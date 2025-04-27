package com.ecom.ecommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {

	private long id;
	
	private long userId;
	
	private List<CartItemDTO> items;
	
}
