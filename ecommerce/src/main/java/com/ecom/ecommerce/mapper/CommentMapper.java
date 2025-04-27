package com.ecom.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecom.ecommerce.dto.CommentDTO;
import com.ecom.ecommerce.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "userId", source = "user.id")
	CommentDTO toDTO(Comment comment);
	
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "product", ignore = true)
	Comment toEntity(CommentDTO commentDTO);
}
