package com.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.product.dto.CategoryDto;
import com.product.request.AddCategoryRequest;
import com.product.request.UpdateCategoryRequest;

public interface CategoryService {
	
	CategoryDto addCategory(AddCategoryRequest request,MultipartFile image);
	
	CategoryDto updateCategory(Integer categoryId,UpdateCategoryRequest request, MultipartFile image);
	
	void deleteCategory(Integer categoryId);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto getById(Integer categoryId);

}
