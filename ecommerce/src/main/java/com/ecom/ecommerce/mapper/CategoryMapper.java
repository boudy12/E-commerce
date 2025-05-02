package com.ecom.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecom.ecommerce.dto.CategoryDTO;
import com.ecom.ecommerce.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	@Mapping(target = "id", source = "id")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", source = "id")
    Category toEntity(CategoryDTO categoryDTO);
    
    List<CategoryDTO> toDTOList(List<Category> categories);
    List<Category> toEntityList(List<CategoryDTO> categoryDTOs);
}
