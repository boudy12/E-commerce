package com.ecom.ecommerce.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetials {

	private String message;
	private String details;
	private Date timestamp;
	
	public ErrorDetials(Date timestamp, String message, String details) {
		this.details = details; 
		this.message = message;
		this.timestamp = timestamp;
	}
}
