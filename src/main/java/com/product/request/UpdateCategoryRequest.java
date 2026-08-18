package com.product.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
	
	@NotBlank(message="Category Name is Required")
	private String categoryName;
	
	@NotBlank(message="Description is Required")
    private String cateDescription;
	
	private String catImageUrl;
	
	private String catPublicId;

}
