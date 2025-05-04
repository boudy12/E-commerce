package com.ecom.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecommerce.dto.CategoryDTO;
import com.ecom.ecommerce.dto.ProductDTO;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.mapper.CategoryMapper;
import com.ecom.ecommerce.mapper.ProductMapper;
import com.ecom.ecommerce.model.Category;
import com.ecom.ecommerce.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/";
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final ProductMapper productMapper;
	
	
	@Transactional 
	public CategoryDTO addCategory(CategoryDTO categoryDTO, MultipartFile image) throws Exception  {
	
		Category newCategory = categoryMapper.toEntity(categoryDTO);

		if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
			throw new ResourceNotFoundException("Category with name " + categoryDTO.getName() + " already exists");
		}
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = saveImage(image);
                newCategory.setImage("/images/" + fileName);
            } catch (IOException e) {
                throw new Exception("Error occurred while saving image: " + e.getMessage());
            }
        }
		
		categoryRepository.save(newCategory);
		return categoryMapper.toDTO(newCategory);
		
	}
	
    private String saveImage(MultipartFile image) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File saveFile = new File(directory, fileName);
        image.transferTo(saveFile);

        return fileName;
   }
    
	@Transactional 
	public CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO, MultipartFile image) throws Exception {
	
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		
		Optional<Category> existingCategory = categoryRepository.findFirstByName(categoryDTO.getName());

		if (existingCategory.isPresent() && existingCategory.get().getId() != categoryDTO.getId()) {
		    throw new Exception("Category name (" + categoryDTO.getName() + ") already exists");
		}
		
		category.setName(categoryDTO.getName());
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = saveImage(image);
                category.setImage("/images/" + fileName);
            } catch (IOException e) {
                throw new Exception("Error occurred while saving image: " + e.getMessage());
            }
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
	}
	
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		categoryRepository.delete(category);
	}
	
	public List<CategoryDTO> getAllCategories(){
		List<Category> categories = categoryRepository.findAll();
		return categoryMapper.toDTOList(categories);
	}

	public List<ProductDTO> getAllProductsByCategory(Long categoryId){
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		return category.getProducts().stream().map(productMapper ::toDTO).collect(Collectors.toList());
	}
}
