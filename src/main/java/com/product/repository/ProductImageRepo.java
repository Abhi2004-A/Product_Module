package com.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer>{

}
