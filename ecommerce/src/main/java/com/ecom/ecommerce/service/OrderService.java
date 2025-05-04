package com.ecom.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import com.ecom.ecommerce.dto.CartDTO;
import com.ecom.ecommerce.dto.OrderDTO;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.mapper.CartMapper;
import com.ecom.ecommerce.mapper.OrderMapper;
import com.ecom.ecommerce.model.Cart;
import com.ecom.ecommerce.model.Order;
import com.ecom.ecommerce.model.Order.OrderStatus;
import com.ecom.ecommerce.model.OrderItem;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.OrderRepository;
import com.ecom.ecommerce.repository.ProductRepository;
import com.ecom.ecommerce.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final EmailService emailService;
    private final CartMapper cartMapper;

	
    @Transactional
    public OrderDTO createOrder(Long userId, String address, String phoneNumber) {
    	
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User not found ."));
    	
		if(!user.isEmailConfirmation()) {
			throw new IllegalStateException("Email not confirmed , Please confirm email before placing order");
		}
		CartDTO cartDTO = cartService.getCart(userId);
		Cart cart = cartMapper.toEntity(cartDTO);
		if(cart.getItems().isEmpty()) {
			throw new IllegalStateException("Cannot create an order with an empty cart ");
		}
		
		Order order = new Order();
		order.setAddress(address);
		order.setCreatedAt(LocalDateTime.now());
		order.setPhoneNumber(phoneNumber);
		order.setStatus(OrderStatus.PREPARING);
		order.setUser(user);
		List<OrderItem> orderItems = createOrderItems(cart,order);
		order.setItems(orderItems);
		BigDecimal totalPrice = orderItems.stream()
			    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
			    .reduce(BigDecimal.ZERO, BigDecimal::add);
		order.setTotalPrice(totalPrice);
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(userId);
		
		try {
			emailService.sendOrderConfirmation(savedOrder);
		} catch (MailException e) {
			logger.error("Failed to send order confirmation email for order ID : " +savedOrder.getId(), e);
		}
		return orderMapper.toDTO(savedOrder);
    }
	public List<OrderItem> createOrderItems(Cart cart, Order order){
		return cart.getItems().stream().map(cartItem-> {
			Product product = productRepository.findById(cartItem.getProduct().getId())
					.orElseThrow(()-> new EntityNotFoundException("Product not found with id : " +cartItem.getProduct().getId()));
			
			if(product.getQuantity() == 0) {
				throw new IllegalStateException("product quantity is not set for product " + product.getName());
			}
			if(product.getQuantity() < cartItem.getQuantity()) {
				throw new IllegalStateException("not enough stock for product "+ product.getName());
			}
			product.setQuantity(product.getQuantity()-cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
		}).collect(Collectors.toList());
	}
	
	public List<OrderDTO> getAllOrders(){
		return orderMapper.toDTOs(orderRepository.findAll());
	}
	
	public List<OrderDTO> getUserOrders(Long userId){
		return orderMapper.toDTOs(orderRepository.findByUserId(userId));	
	}
	
	public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
	    order.setStatus(status);
	    Order savedOrder = orderRepository.save(order);
	    emailService.chabgeOrderStatus(savedOrder, status);
	    return orderMapper.toDTO(savedOrder);
	}
}

