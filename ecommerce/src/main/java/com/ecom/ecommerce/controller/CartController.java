package com.ecom.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.dto.CartDTO;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/add")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartDTO> addToCart(@AuthenticationPrincipal UserDetails userDetails,
											 @RequestParam Long productId,
											 @RequestParam int quantity){
	
		Long userId = ((User) userDetails).getId();	
		
		return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity));
	}
	
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal UserDetails userDetails){
		Long userId = ((User) userDetails).getId();	
		
		return ResponseEntity.ok(cartService.getCart(userId));
	}
	
	
	@DeleteMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails){
		Long userId = ((User) userDetails).getId();	
		cartService.clearCart(userId);
 		return ResponseEntity.noContent().build();
	}
	
}
