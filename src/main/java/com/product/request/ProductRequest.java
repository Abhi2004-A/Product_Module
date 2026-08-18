package com.product.request;

import com.product.entity.Brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {

	@NotBlank(message = "Please Add Product Name")
	private String productName;
	
	@NotNull(message = "Price is Required")
	@Positive(message = "Price Must be Greater Then Zero")
	private Double price;
	
	private String description;
	
	@NotBlank(message = "Brand Name is Required")
	private String brandName; 
	
	@NotBlank(message = "Category Name is Required")
	private String categoryName;

}
