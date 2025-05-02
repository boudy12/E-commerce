package com.ecom.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecom.ecommerce.dto.CommentDTO;
import com.ecom.ecommerce.dto.ProductDTO;
import com.ecom.ecommerce.model.Comment;
import com.ecom.ecommerce.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {	

    @Mapping(target = "categoryId",  source = "category.id")
	@Mapping(target = "image", source = "image")
	ProductDTO toDTO(Product product);
	
    @Mapping(target = "category.id",  source = "categoryId")
	@Mapping(target = "image", source = "image")
	Product toEntity(ProductDTO productDTO);
	
	
	@Mapping(target = "userId", source = "user.id")
	CommentDTO toDTO(Comment comment);
	
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "product", ignore = true)
	Comment toEntity(CommentDTO commentDTO);
}
