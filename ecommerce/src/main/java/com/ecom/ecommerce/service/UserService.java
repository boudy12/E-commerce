package com.ecom.ecommerce.service;

import com.ecom.ecommerce.dto.ChangePasswordRequest;
import com.ecom.ecommerce.model.User;

public interface UserService {

	User registerUser(User user);
	
	public User getUserByEmail(String email);
	
	public void changePassword(String email, ChangePasswordRequest request);


}
