package com.ecom.ecommerce.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.ChangePasswordRequest;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	@Override
	public User registerUser(User user) {
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		if(optionalUser.isPresent()) {
			throw new IllegalStateException("Email is Already Exist");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(User.Role.USER);
		user.setConfirmationCode(generateConfirmationCode());
		user.setEmailConfirmation(false);
		emailService.sendConfirmationCode(user);
		userRepository.save(user);
		return user;
	}
	
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
				()-> new ResourceNotFoundException("User not found !"));
	}
	
	@Override
	public void changePassword(String email, ChangePasswordRequest request) {
		User user = getUserByEmail(email);
		if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new BadCredentialsException("Current Password is incorrect");
		}
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		userRepository.save(user);
		
	}
	
	@Override
	public void confirmEmail(String email, String code) {
		User user = userRepository.findByEmail(email).orElseThrow(
				()-> new ResourceNotFoundException("User not found !"));
		if(code.equals(user.getConfirmationCode())) {
			user.setConfirmationCode(null);
			user.setEmailConfirmation(true);
			userRepository.save(user);
		}else {
			throw new BadCredentialsException("Invalid confirmation code");
		}
	}
	
	@Override
	public String generateConfirmationCode() {
		Random random = new Random();
		int code = 10000 + random.nextInt(90000);
		return String.valueOf(code);
	}
	
}
