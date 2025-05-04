package com.ecom.ecommerce.controller;

import org.springframework.http.MediaType;

import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecommerce.dto.ProductDTO;
import com.ecom.ecommerce.dto.ProductListDTO;
import com.ecom.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") @Valid ProductDTO productDTO, 
												@RequestPart(value = "image",required = false) MultipartFile image )throws Exception{
		
		return ResponseEntity.ok(productService.createProduct(productDTO, image));
	}
	
	
	@PutMapping(value ="/{id}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id ,
													@RequestPart("product") @Valid ProductDTO productDTO, 
													@RequestPart(value = "image",required = false) MultipartFile image )throws Exception{
		
		return ResponseEntity.ok(productService.updateProduct(id, productDTO, image));
	}
	
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().body("Deleted Successfuly");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProduct(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductListDTO>> getAllProducts(@PageableDefault(size = 10) Pageable pageable ) {
		return ResponseEntity.ok(productService.getAllProducts(pageable));
	}
	
}
