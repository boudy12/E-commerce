package com.ecom.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {

	private Long id;
	
	@NotBlank(message = "Content cannot be empty")
	private String content;
	
	@Min(value = 1, message = "Score must be at least 1")
	@Max(value = 5, message = "Score cannot be more than 5")
	private int score;
	
	
	private Long userId;
}
