package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.ProductImageDto;
import com.product.request.AddproductImages;
import com.product.response.ApiResponse;
import com.product.service.ProductImageService;

@RestController
@RequestMapping("/productimage")
public class ProductImageController {
	
	@Autowired
	private ProductImageService piservice;
	
	@PostMapping("/uploadimage")
	public ResponseEntity<?> uploadproductimage(@RequestPart("request") AddproductImages request, @RequestPart("image") MultipartFile image){
		ProductImageDto pidto=piservice.addProductImage(request, image);
		return ResponseEntity.ok(new ApiResponse<>("Product Image Uploaded Successfully!",pidto,HttpStatus.OK));
	}
	
	@PutMapping("/updateimage/{imageId}")
	public ResponseEntity<?> updateProductImages(@PathVariable Integer imageId, @RequestPart MultipartFile image){
		ProductImageDto pdto=piservice.updateProductImage(imageId, image);
		return ResponseEntity.ok(new ApiResponse<>("Product Image Updated successfully!", pdto, HttpStatus.OK));
	}

}
