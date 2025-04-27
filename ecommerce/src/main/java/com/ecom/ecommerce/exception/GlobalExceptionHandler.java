package com.ecom.ecommerce.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ecom.ecommerce.dto.ErrorDetials;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundExcepion(ResourceNotFoundException exception, WebRequest request){
		
		ErrorDetials detials = new ErrorDetials(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(detials,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<?> handleInsufficientStockException(InsufficientStockException exception, WebRequest request){
		
		ErrorDetials detials = new ErrorDetials(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(detials,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
		
		ErrorDetials detials = new ErrorDetials(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(detials,HttpStatus.NOT_FOUND);
	}
	
}
