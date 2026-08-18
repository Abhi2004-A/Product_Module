package com.product.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.CategoryDto;
import com.product.entity.Category;
import com.product.exception.ProductException;
import com.product.repository.CategoryRepo;
import com.product.request.AddCategoryRequest;
import com.product.request.UpdateCategoryRequest;
import com.product.response.CloudinaryResponse;
import com.product.service.CategoryService;
import com.product.service.CloudinaryService;

@Service
public class CategoryServiceImp implements CategoryService{
	
	@Autowired
	private CategoryRepo crepo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CloudinaryService cservice;
	
	@Override
	public CategoryDto addCategory(AddCategoryRequest request, MultipartFile image) {
		Category ifExists=crepo.findByCategoryName(request.getCategoryName()).orElse(null);
		if(ifExists!=null) {
			throw new ProductException("Category Already Exists!", HttpStatus.CONFLICT);
		}
		
		if(image==null || image.isEmpty()) {
			throw new ProductException("Category Image is Required", HttpStatus.BAD_REQUEST);
		}
		
		
		Category c=new Category();
		c=mapper.map(request, Category.class);
		CloudinaryResponse response=cservice.uploadImage(image);
		c.setCatPublicId(response.getPublicId());
		c.setCatImageUrl(response.getImageUrl());
		c=crepo.save(c);
		return mapper.map(c, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(Integer categoryId,UpdateCategoryRequest request, MultipartFile image) {
		Category checkCate=crepo.findById(categoryId).orElseThrow(()->new ProductException("Category Not Found!", HttpStatus.NOT_FOUND));
		
		Category ifExists=crepo.findByCategoryName(request.getCategoryName()).orElse(null);
		
		if(ifExists!=null && !ifExists.getCategoryId().equals(categoryId)) {
			throw new ProductException("Category Already Exists!", HttpStatus.CONFLICT);
		}
		
		if(image!=null && !image.isEmpty()) {
			if(checkCate.getCatImageUrl()!=null&&checkCate.getCatPublicId()!=null) {
				cservice.deleteImage(checkCate.getCatPublicId());
			}
			CloudinaryResponse response=cservice.uploadImage(image);
			checkCate.setCatPublicId(response.getPublicId());
			checkCate.setCatImageUrl(response.getImageUrl());
		}
		
		mapper.map(request, checkCate);
		checkCate=crepo.save(checkCate);
		return mapper.map(checkCate, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category ifExists=crepo.findById(categoryId).orElseThrow(()->new ProductException("Category Not Found!", HttpStatus.NOT_FOUND));
		if(ifExists.getCatPublicId()!=null) {
			cservice.deleteImage(ifExists.getCatPublicId());
		}
		crepo.deleteById(categoryId);
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		return crepo.findAll()
				    .stream()
				    .map((c)->mapper.map(c, CategoryDto.class))
				    .collect(Collectors.toList());
	}

	@Override
	public CategoryDto getById(Integer categoryId) {
		Category ifExists=crepo.findById(categoryId).orElseThrow(()->new ProductException("Category Not Found!", HttpStatus.NOT_FOUND));
		return mapper.map(ifExists, CategoryDto.class);
	}

}
