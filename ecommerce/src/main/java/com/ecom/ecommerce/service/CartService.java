package com.ecom.ecommerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.CartDTO;
import com.ecom.ecommerce.exception.InsufficientStockException;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.mapper.CartMapper;
import com.ecom.ecommerce.model.Cart;
import com.ecom.ecommerce.model.CartItem;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.CartRepository;
import com.ecom.ecommerce.repository.ProductRepository;
import com.ecom.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private CartRepository cartRepository;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private CartMapper cartMapper;
	
	@Autowired
	public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartMapper cartMapper) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.cartMapper = cartMapper;
	}
	
	
	public CartDTO addToCart(Long userId, Long productId, int quantity) {
		
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));  
        
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User not found ."));
		
		if(product.getQuantity() < quantity) {
			throw new InsufficientStockException("Not enough available");
		}
		
		
		Cart cart = cartRepository.findByUserId(userId)
				.orElse(new Cart(user, new ArrayList<>()));  
		
		Optional<CartItem> existingCartItem = cart.getItems().stream()
				.filter(item -> item.getProduct().getId()== productId).findFirst();
		
		
		if(existingCartItem.isPresent()) {
			existingCartItem.get().setQuantity(existingCartItem.get().getQuantity()+quantity);
		}else {
			CartItem cartItem = new CartItem(cart, product, quantity);
			cart.getItems().add(cartItem);
		}
		
		Cart savedCart = cartRepository.save(cart);
		
		return cartMapper.toDTO(savedCart);
		
	}
	
	
	public CartDTO getCart(Long userId) {
		
		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not Found "));  
		
		return cartMapper.toDTO(cart);
	}
	
	public void clearCart(Long userId) {
		
		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not Found "));    
		
		cart.getItems().clear();
		cartRepository.save(cart);
	}
	
	
	
}
