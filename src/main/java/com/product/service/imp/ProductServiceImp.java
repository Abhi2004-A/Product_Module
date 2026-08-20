package com.product.service.imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.product.dto.BrandDto;
import com.product.dto.ProductDto;
import com.product.entity.Brand;
import com.product.entity.Category;
import com.product.entity.ProductImage;
import com.product.entity.Products;
import com.product.exception.ProductException;
import com.product.repository.BrandRepo;
import com.product.repository.CategoryRepo;
import com.product.repository.ProductImageRepo;
import com.product.repository.ProductRepo;
import com.product.request.ProductRequest;
import com.product.request.UpdateProductRequest;
import com.product.response.CloudinaryResponse;
import com.product.service.CloudinaryService;
import com.product.service.ProductImageService;
import com.product.service.ProductService;

@Service
public class ProductServiceImp implements ProductService{
	
	@Autowired
	private ProductRepo prepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private BrandRepo brepo;
	
	@Autowired
	private CategoryRepo crepo;
	
	@Autowired
	private CloudinaryService cservice;
	
	@Autowired
	private ProductImageService piservice;
	
	@Autowired
	private ProductImageRepo pirepo;

	@Transactional
	@Override
	public ProductDto addProduct(ProductRequest request, List<MultipartFile> images) {
//		Products alreadyExists=prepo.findByproductName(request.getProductName()).orElse(null);
//		if(alreadyExists!=null) {
//			throw new ProductException("Product Already Exists!", HttpStatus.CONFLICT);
//		}
		Brand ifExists=brepo.findByBrandName(request.getBrandName()).orElseThrow(()->new ProductException("Brand Is Not Found!", HttpStatus.NOT_FOUND));
		
		Category alreadyExists=crepo.findByCategoryName(request.getCategoryName()).orElseThrow(()->new ProductException("Category Is Not Found!",HttpStatus.NOT_FOUND));
		
		Products p=mapper.map(request, Products.class);	
		p.setBrand(ifExists);
		p.setCategory(alreadyExists);
		p=prepo.save(p);
		
		if(images !=null && !images.isEmpty()) {
			for(MultipartFile image:images) {
				if(image==null || image.isEmpty()) {
					throw new ProductException("image is Required!", HttpStatus.BAD_REQUEST);
				}
				CloudinaryResponse response=cservice.uploadImage(image);
				ProductImage pi=new ProductImage();
				pi.setProductPublicId(response.getPublicId());
				pi.setProductImagePath(response.getImageUrl());
				pi.setProducts(p);
				pirepo.save(pi);
			}
		}
		return mapper.map(p, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		return prepo.findAll()
				    .stream()
				    .map((p)->mapper.map(p, ProductDto.class))
				    .collect(Collectors.toList());
	}

	@Override
	public ProductDto updateProduct(Integer productId,UpdateProductRequest request, List<MultipartFile> images) {
		Products p=prepo.findById(productId).orElseThrow(()->new ProductException("Product Not Found!", HttpStatus.NOT_FOUND));
		
		Brand ifExistsbrand=brepo.findByBrandName(request.getBrandName()).orElseThrow(()->new ProductException("Brand Not Found", HttpStatus.NOT_FOUND));
		
		Category ifExistscate=crepo.findByCategoryName(request.getCategoryName()).orElseThrow(()->new ProductException("Category Is Not Found!",HttpStatus.NOT_FOUND));
		
		mapper.map(request, p);
		p.setBrand(ifExistsbrand);
		p.setCategory(ifExistscate);
		
		if(images!=null && !images.isEmpty()) {
			List<ProductImage> productimage=pirepo.findByProducts(p);
			for(ProductImage pi: productimage) {
				cservice.deleteImage(pi.getProductPublicId());
				pirepo.delete(pi);
			}
			for(MultipartFile image:images) {
				if(image==null || image.isEmpty()) {
					throw new ProductException("Image is Required!", HttpStatus.BAD_REQUEST);
				}
				CloudinaryResponse response=cservice.uploadImage(image);
				ProductImage pimage=new ProductImage();
				pimage.setProductPublicId(response.getPublicId());
				pimage.setProductImagePath(response.getImageUrl());
				pimage.setProducts(p);
				pirepo.save(pimage);
			}
		}
		
		p=prepo.save(p);
		return mapper.map(p, ProductDto.class);
	}
	
	@Override
	public void deleteProduct(Integer productId) {
		Products p=prepo.findById(productId).orElseThrow(()->new ProductException("Product Not Found!", HttpStatus.NOT_FOUND));
		
		if(p.getProductimage()!=null) {
			for(ProductImage productimage:p.getProductimage()) {
				if(productimage.getProductPublicId()!=null) {
					cservice.deleteImage(productimage.getProductPublicId());
				}
			}
		}
		prepo.deleteById(productId);
		
	}

	@Override
	public ProductDto getByProductId(Integer productId) {
		Products p=prepo.findById(productId).orElseThrow(()->new ProductException("Product Not Found!", HttpStatus.NOT_FOUND));
		return mapper.map(p, ProductDto.class);
	}

}
