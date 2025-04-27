package com.ecom.ecommerce.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.CommentDTO;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.mapper.CommentMapper;
import com.ecom.ecommerce.model.Comment;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.CommentRepository;
import com.ecom.ecommerce.repository.ProductRepository;
import com.ecom.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private CommentRepository commentRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private CommentMapper commentMapper;
	
	@Autowired
	public CommentService(CommentRepository commentRepository, ProductRepository productRepository, UserRepository userRepository, CommentMapper commentMapper) {
		this.commentRepository = commentRepository;
		this.productRepository =productRepository;
		this.userRepository = userRepository;
		this.commentMapper = commentMapper;
	}
	
	
	public CommentDTO addComment(Long productId, Long userId, CommentDTO commentDTO)throws Exception {
		
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));  
        
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User not found ."));
		
		Comment comment = commentMapper.toEntity(commentDTO);
		
		comment.setContent(commentDTO.getContent());
		comment.setProduct(product);
		comment.setScore(commentDTO.getScore());
		comment.setUser(user);
		Comment savedComment = commentRepository.save(comment);
		
		return commentMapper.toDTO(savedComment);
	}
	
	public List<CommentDTO> getAllCommentByProductId(Long productId){
		
		List<Comment> comments = commentRepository.findByProductId(productId);
	
		return comments.stream()
				.map(commentMapper :: toDTO)
				.collect(Collectors.toList());
	}
	
	
	
	
}
