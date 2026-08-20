package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductImage;
import com.product.entity.Products;

import java.util.List;


@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer>{
	
	List<ProductImage> findByProducts(Products products);

}
