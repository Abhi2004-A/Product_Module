package com.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.product.dto.ProductDto;
import com.product.entity.Products;
import com.product.request.ProductRequest;
import com.product.request.UpdateProductRequest;

public interface ProductService {
	
	ProductDto addProduct(ProductRequest request, List<MultipartFile> images);
	
	List<ProductDto> getAllProduct();
	
	ProductDto updateProduct(Integer productId,UpdateProductRequest request, List<MultipartFile> images);
	
	ProductDto getByProductId(Integer productId);
	
	void deleteProduct(Integer productId);

}
