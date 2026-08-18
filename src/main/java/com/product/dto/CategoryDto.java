package com.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	private String categoryName;
	
	private String cateDescription;
	
	private String cateImageUrl;

}
