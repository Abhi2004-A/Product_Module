package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

import com.product.dto.ProductDto;
import com.product.exception.ProductException;
import com.product.request.ProductRequest;
import com.product.request.UpdateProductRequest;
import com.product.response.ApiResponse;
import com.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService pservice;

	@PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addProduct(@RequestParam String productName,@RequestParam Double price,@RequestParam String description,@RequestParam String brandName,@RequestParam String categoryName, @RequestPart List<MultipartFile> images){
		ProductRequest request =new ProductRequest();
		request.setProductName(productName);
		request.setPrice(price);
		request.setDescription(description);
		request.setBrandName(brandName);
		request.setCategoryName(categoryName);
		ProductDto dto=pservice.addProduct(request,images);
		return ResponseEntity.ok(new ApiResponse<>("Product Added Successfully!", dto, HttpStatus.OK));
	}
	
	@GetMapping("/getproduct")
	public ResponseEntity<?> getAllProducts(){
	    List<ProductDto> ldto=pservice.getAllProduct();
		return ResponseEntity.ok(new ApiResponse<>("All Products!", ldto, HttpStatus.OK));
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<?> updateproducts(@PathVariable Integer productId,@RequestPart UpdateProductRequest request, @RequestPart List<MultipartFile> images){
		ProductDto dto=pservice.updateProduct(productId,request,images);
		return ResponseEntity.ok(new ApiResponse<>("Product Updated Successfully!", dto, HttpStatus.OK));
	}
	
	@GetMapping("/getById/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Integer productId){
		ProductDto dto=pservice.getByProductId(productId);
		return ResponseEntity.ok(new ApiResponse<>("Your Selected Product!", dto, HttpStatus.OK));
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deletebyId(@PathVariable Integer productId){
		pservice.deleteProduct(productId);
		return ResponseEntity.ok(new ApiResponse<>("Product Deleted Successfully!", null, HttpStatus.OK));
	}
}
