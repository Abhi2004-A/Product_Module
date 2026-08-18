package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Brand;


@Repository
public interface BrandRepo extends JpaRepository<Brand, Integer>{
	
	Optional<Brand> findByBrandName(String brandName);
}
