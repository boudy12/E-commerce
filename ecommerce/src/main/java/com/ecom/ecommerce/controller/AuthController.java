package com.ecom.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.dto.ChangePasswordRequest;
import com.ecom.ecommerce.dto.LoginRequest;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.service.JwtService;
import com.ecom.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	
	@Autowired
	public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
				);
		UserDetails userDetails = userService.getUserByEmail(loginRequest.getEmail());
		String jwt = jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(jwt);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@Valid @RequestBody User user){
		return ResponseEntity.ok(userService.registerUser(user));
	
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		userService.changePassword(email, changePasswordRequest);
		return ResponseEntity.ok().body("Password Changed Successfuly");
	}
}
