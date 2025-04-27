package com.ecom.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.model.Order;

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
		
		simpleMailMessage.setFrom(formEmail);
		simpleMailMessage.setTo(order.getUser().getEmail());
		simpleMailMessage.setSubject("Order Confirmation");
		simpleMailMessage.setText("Your order has been Confirmed . Order ID : " + order.getId());
		javaMailSender.send(simpleMailMessage);
	}
}
