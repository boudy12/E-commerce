package com.ecom.ecommerce.service;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecommerce.dto.ProductDTO;
import com.ecom.ecommerce.dto.ProductListDTO;
import com.ecom.ecommerce.exception.ResourceNotFoundException;
import com.ecom.ecommerce.mapper.ProductMapper;
import com.ecom.ecommerce.model.Category;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.repository.CategoryRepository;
import com.ecom.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ProductMapper productMapper;
	
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/";
	

	
	
	@Transactional
	public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws Exception{
		Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		
        Product product = productMapper.toEntity(productDTO);
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = saveImage(image);
                product.setImage("/images/" + fileName);
            } catch (IOException e) {
                throw new Exception("Error occurred while saving image: " + e.getMessage());
            }
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
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
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile image) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));   	
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        
        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        existingProduct.setCategory(category.get());
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = saveImage(image);
                existingProduct.setImage("/images/" + fileName);
            } catch (IOException e) {
                throw new Exception("Error occurred while saving image: " + e.getMessage());
            }
        }
        Product savedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(savedProduct);
        
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product Product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));  
        productRepository.delete(Product);
    }

    
    
    public ProductDTO getProduct(Long id) {
        Product Product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));  
        return productMapper.toDTO(Product);
    }
    
    public Page<ProductListDTO> getAllProducts(Pageable pageable){
    	
    	return productRepository.findAllWithoutComment(pageable);
    	
    }
    
    
    
	
}
