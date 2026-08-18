package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Integer>{
	
	Optional<Products> findByProductName(String productName);
}
