package com.ecom.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.dto.OrderDTO;
import com.ecom.ecommerce.model.Order;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.service.OrderService;


@RestController
@RequestMapping("/api/order")
public class OrderController {

	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<OrderDTO> createOrder(@AuthenticationPrincipal UserDetails userDetails, 
												@RequestParam String address,
												@RequestParam String phoneNumber){
		
		Long userId = ((User) userDetails).getId();
		OrderDTO orderDTO = orderService.createOrder(userId, address, phoneNumber);
		return ResponseEntity.ok(orderDTO);
		
	}
	
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrderDTO>> getAllOrders(){
		List<OrderDTO> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}
	
	
	@GetMapping("/user")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<OrderDTO>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails){
		Long userId = ((User) userDetails).getId();
		List<OrderDTO> orders = orderService.getUserOrders(userId);
		return ResponseEntity.ok(orders);
	}
	

	@PutMapping("/{orderId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status){
		OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
		return ResponseEntity.ok(updatedOrder);
	}
	
}
