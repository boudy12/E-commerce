package com.ecom.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.dto.CommentDTO;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/product/{productId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CommentDTO> addComment(@PathVariable Long productId, 
												@AuthenticationPrincipal UserDetails userDetails, 
												@Valid @RequestBody CommentDTO commentDTO) throws Exception{
		
		Long userId = ((User) userDetails).getId();
		return ResponseEntity.ok(commentService.addComment(productId, userId, commentDTO));
	}
	
	@GetMapping("/product/{productId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<CommentDTO>> getCommentsByProduct(@PathVariable Long productId){
		
		return ResponseEntity.ok(commentService.getAllCommentByProductId(productId)); 
	}
}
