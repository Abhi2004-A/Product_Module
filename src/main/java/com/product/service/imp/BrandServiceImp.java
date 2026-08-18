package com.product.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.product.dto.BrandDto;
import com.product.entity.Brand;
import com.product.exception.ProductException;
import com.product.repository.BrandRepo;
import com.product.request.AddBrandRequest;
import com.product.request.UpdateBrandRequest;
import com.product.response.CloudinaryResponse;
import com.product.service.BrandService;
import com.product.service.CloudinaryService;

@Service
public class BrandServiceImp implements BrandService{
	
	@Autowired
	private BrandRepo brepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CloudinaryService cservice;

	@Override
	public BrandDto addBrand(AddBrandRequest request, MultipartFile image) {
		
		Brand alreadyExists=brepo.findByBrandName(request.getBrandName()).orElse(null);
		
		if(alreadyExists!=null) {
			throw new ProductException("Brand Already Exists!", HttpStatus.CONFLICT);
		}
		
		if(image==null || image.isEmpty()) {
			throw new ProductException("Brand Image is Required", HttpStatus.BAD_REQUEST);
		}
		
		Brand b=new Brand();
		b=mapper.map(request, Brand.class);
		CloudinaryResponse response=cservice.uploadImage(image);
		b.setPublicId(response.getPublicId());
		b.setBrandImagePath(response.getImageUrl());
		b=brepo.save(b);
		return mapper.map(b, BrandDto.class);
	}

	@Override
	public BrandDto updateBrand(Integer brandId, UpdateBrandRequest request, MultipartFile image) {
		
		Brand brand=brepo.findById(brandId).orElse(null);
		
		if(brand==null) {
			throw new ProductException("Brand Not Found!", HttpStatus.NOT_FOUND);
		}
		
		Brand ifExists=brepo.findByBrandName(request.getBrandName()).orElse(null);
		
		if(ifExists!=null && !ifExists.getBrandId().equals(brandId)) {
			throw new ProductException("Brand Already Exists!", HttpStatus.CONFLICT);
		}
		
		if(image !=null && !image.isEmpty()) {
			if(brand.getBrandImagePath()!=null && brand.getPublicId()!=null) {
				cservice.deleteImage(brand.getPublicId());
			}
			CloudinaryResponse response=cservice.uploadImage(image);
			brand.setBrandImagePath(response.getImageUrl());
			brand.setPublicId(response.getPublicId());
		}
		
		mapper.map(request, brand);
		brand=brepo.save(brand);
		return mapper.map(brand, BrandDto.class);
	}

	@Override
	public void deleteBrand(Integer brandId) {
		Brand ifExists=brepo.findById(brandId).orElseThrow(()->new ProductException("Brand Not Found!", HttpStatus.NOT_FOUND));
		if(ifExists.getPublicId()!=null) {
			cservice.deleteImage(ifExists.getPublicId());
		}
		brepo.deleteById(brandId);
		
	}

	@Override
	public List<BrandDto> getAllBrand() {
		return brepo.findAll()
				    .stream()
				    .map((b)->mapper.map(b, BrandDto.class))
				    .collect(Collectors.toList());
	}

	@Override
	public BrandDto getByBrandId(Integer brandId) {
		Brand b=brepo.findById(brandId).orElseThrow(()->new ProductException("Brand Not Found!", HttpStatus.NOT_FOUND));
		return mapper.map(b, BrandDto.class);
	}

}
