package com.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	private Integer productId;
	
	private String productName;
	
	private String description;
	
	private Double price;
	
	private LocalDateTime addedAt;
	
	private Integer brandId;
	
	private Integer categoryId;
	
	private List<ProductImageDto> productimages;
	

}
