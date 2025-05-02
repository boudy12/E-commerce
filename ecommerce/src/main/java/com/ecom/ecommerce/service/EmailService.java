package com.ecom.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.model.Order;
import com.ecom.ecommerce.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String formEmail;
	
	
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
	public void sendOrderConfirmation(Order order) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("bodynafea2@gmail.com");
		simpleMailMessage.setTo(order.getUser().getEmail());
		simpleMailMessage.setSubject("Order Confirmation");
		simpleMailMessage.setText("Your order has been Confirmed . Order ID : " + order.getId() + " , and total price is "+ order.getTotalPrice());
		javaMailSender.send(simpleMailMessage);

		
	}
	
	
	
	public void sendConfirmationCode(User user) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("bodynafea2@gmail.com");
		simpleMailMessage.setTo(user.getEmail());
		simpleMailMessage.setSubject("Confirmation your email ..");
		simpleMailMessage.setText("Please confirm your email by entering this code : " + user.getConfirmationCode());
		javaMailSender.send(simpleMailMessage);
		
	}
	
	public void chabgeOrderStatus(Order order, Order.OrderStatus status) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(order.getUser().getEmail());
		message.setSubject("Order Updated..");
		message.setFrom(formEmail);
		message.setText("Order Status changed to " + status);
		
		javaMailSender.send(message);
		
	}
}
