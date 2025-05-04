package com.ecom.ecommerce.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecommerce.dto.CategoryDTO;
import com.ecom.ecommerce.dto.ProductDTO;
import com.ecom.ecommerce.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> addCategory(@RequestPart("category") @Valid CategoryDTO categoryDTO,
													@RequestPart(value ="image", required = false) MultipartFile image) throws Exception{
		return ResponseEntity.ok(categoryService.addCategory(categoryDTO, image));
	}
	
	
	@PutMapping("/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@RequestPart("category") @Valid CategoryDTO categoryDTO,
												@RequestPart(value ="image", required = false) MultipartFile image) throws Exception{
		return ResponseEntity.ok(categoryService.updateCategory(categoryId,categoryDTO, image));
	}
	
	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) throws Exception{
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok().body("Deleted Successfuly");
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{categoryId}/products")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long categoryId){
		return ResponseEntity.ok(categoryService.getAllProductsByCategory(categoryId));
	}
}
