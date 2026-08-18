package com.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateProductRequest {
	
	@NotBlank(message="Product Name is Required")
	private String productName;
	
	@NotBlank(message="Price is required")
	@Positive(message="Price Must be Greater Then Zero")
	private Double price;
	
	@NotBlank(message="Description is Required")
	private String description;
	
	@NotBlank(message="Brand Name is Required")
	private String brandName;
	
	@NotBlank(message="Category Name is Required")
	private String categoryName;

}
