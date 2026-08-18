package com.product.service.imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.ProductImageDto;
import com.product.entity.ProductImage;
import com.product.entity.Products;
import com.product.exception.ProductException;
import com.product.repository.ProductImageRepo;
import com.product.repository.ProductRepo;
import com.product.request.AddproductImages;
import com.product.response.CloudinaryResponse;
import com.product.service.CloudinaryService;
import com.product.service.ProductImageService;

@Service
public class ProductImageServiceImp implements ProductImageService{
	
	@Autowired
	private ProductImageRepo pirepo;
	
	@Autowired
	private ProductRepo prepo;
	
	@Autowired
	private CloudinaryService cservice;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductImageDto addProductImage(AddproductImages request, MultipartFile image) {
		Products p=prepo.findById(request.getProductId()).orElseThrow(()->new ProductException("Product Not Found", HttpStatus.NOT_FOUND));
		if(image==null || image.isEmpty()) {
			throw new ProductException("Image is Required", HttpStatus.BAD_REQUEST);
		}
		CloudinaryResponse response=cservice.uploadImage(image);
		ProductImage productimage=new ProductImage();
		productimage.setProductImagePath(response.getImageUrl());
		productimage.setProductPublicId(response.getPublicId());
		productimage.setProducts(p);
		productimage=pirepo.save(productimage);
		return mapper.map(productimage, ProductImageDto.class);
	}

	@Override
	public ProductImageDto updateProductImage(Integer imageId, MultipartFile image) {
		ProductImage productimage=pirepo.findById(imageId).orElseThrow(()->new ProductException("Image Not Found", HttpStatus.NOT_FOUND));
		
		if(image!=null && !image.isEmpty()) {
			if(productimage.getProductImagePath()!=null && productimage.getProductPublicId()!=null) {
				cservice.deleteImage(productimage.getProductPublicId());
			}
			
			CloudinaryResponse response=cservice.uploadImage(image);
			productimage.setProductPublicId(response.getPublicId());
			productimage.setProductImagePath(response.getImageUrl());
			productimage=pirepo.save(productimage);
			
		}
		return mapper.map(productimage, ProductImageDto.class);
	}

}
