package com.product.service;

import org.springframework.web.multipart.MultipartFile;

import com.product.dto.ProductImageDto;
import com.product.request.AddproductImages;

public interface ProductImageService {
	
	ProductImageDto addProductImage(AddproductImages request, MultipartFile image);
	
	ProductImageDto updateProductImage(Integer imageId, MultipartFile image);

}
