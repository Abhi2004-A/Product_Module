package com.product.request;

import com.product.entity.Brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {

	
	private String productName;
	
	
	private Double price;
	
	private String description;
	
	
	private String brandName; 
	
	
	private String categoryName;

}
