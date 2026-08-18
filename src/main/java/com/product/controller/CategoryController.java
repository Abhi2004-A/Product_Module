package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.CategoryDto;
import com.product.request.AddCategoryRequest;
import com.product.request.UpdateCategoryRequest;
import com.product.response.ApiResponse;
import com.product.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService cservice;
	
	@PostMapping(path = "/addcate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addCate(@RequestParam String categoryName, @RequestParam String cateDescription, @RequestPart MultipartFile image){
		AddCategoryRequest request=new AddCategoryRequest();
		request.setCategoryName(categoryName);
		request.setCateDescription(cateDescription);
		CategoryDto cdto=cservice.addCategory(request,image);
		return ResponseEntity.ok(new ApiResponse<>("Category Added Successfully!",cdto,HttpStatus.OK));
	}
	
	@PutMapping("/updatecat/{categoryId}")
	public ResponseEntity<?> updatecate(@PathVariable Integer categoryId, @RequestPart UpdateCategoryRequest request,@RequestPart MultipartFile image){
		CategoryDto cdto=cservice.updateCategory(categoryId,request,image);
		return ResponseEntity.ok(new ApiResponse<>("Category Updated Successfully!", cdto, HttpStatus.OK));
	}
	
	@DeleteMapping("/deletecate/{categoryId}")
	public ResponseEntity<?> deletecate(@PathVariable Integer categoryId){
		cservice.deleteCategory(categoryId);
		return ResponseEntity.ok(new ApiResponse<>("Category Deleted Successfully!", null, HttpStatus.OK));
	}
	
	@GetMapping("/getallcate")
	public ResponseEntity<?> getallcategorys(){
		List<CategoryDto> clist=cservice.getAllCategory();
		return ResponseEntity.ok(new ApiResponse<>("Category List!", clist, HttpStatus.OK));
	}
	
	@GetMapping("/getbId/{categoryId}")
	public ResponseEntity<?> getCateById(@PathVariable Integer categoryId){
		CategoryDto cdto=cservice.getById(categoryId);
		return ResponseEntity.ok(new ApiResponse<>("Your Selected Category!", cdto, HttpStatus.OK));
	}

}
