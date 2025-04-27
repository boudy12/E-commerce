package com.ecom.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecom.ecommerce.dto.CartDTO;
import com.ecom.ecommerce.dto.CartItemDTO;
import com.ecom.ecommerce.model.Cart;
import com.ecom.ecommerce.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {

	@Mapping(target = "userId", source = "user.id")
	CartDTO toDTO(Cart cart);
	
	@Mapping(target =  "user.id", source = "userId")
	Cart toEntity(CartDTO cartDTO);
	
	
	@Mapping(target = "productID", source = "product.id")
	CartItemDTO toDTO(CartItem cartItem);
	
	@Mapping(target = "product.id", source = "productID")
	CartItem toEntity(CartItemDTO cartItemDTO);
}
