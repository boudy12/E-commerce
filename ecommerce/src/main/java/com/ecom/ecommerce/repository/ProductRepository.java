package com.ecom.ecommerce.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.ecommerce.dto.ProductListDTO;
import com.ecom.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	
	@Query("SELECT new com.ecom.ecommerce.dto.ProductListDTO(p.id, p.name, p.description, p.price, p.quantity, p.image) FROM Product p")
	Page<ProductListDTO> findAllWithoutComment(Pageable pageable);
}
