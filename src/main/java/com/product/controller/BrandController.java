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

import com.product.dto.BrandDto;
import com.product.request.AddBrandRequest;
import com.product.request.UpdateBrandRequest;
import com.product.response.ApiResponse;
import com.product.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandService bservice;
	@PostMapping(path = "/addbrand", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addingbrand(@RequestParam String brandName,@RequestPart("image") MultipartFile image){
		AddBrandRequest request=new AddBrandRequest();
		request.setBrandName(brandName);
		BrandDto brand=bservice.addBrand(request, image);
		
		return ResponseEntity.ok(new ApiResponse<>("Brand Added Successfully!",brand,HttpStatus.OK));
	}
	
	@PutMapping("/updatebrand/{brandId}")
	public ResponseEntity<?> updatebrand(@PathVariable Integer brandId,@RequestPart UpdateBrandRequest request,@RequestPart MultipartFile image){
		BrandDto dto=bservice.updateBrand(brandId, request,image);
		return ResponseEntity.ok(new ApiResponse<>("Brand Updated Successfully!", dto, HttpStatus.OK));
	}
	
	@DeleteMapping("/deletebrand/{brandId}")
	public ResponseEntity<?> deletebrand(@PathVariable Integer brandId){
		bservice.deleteBrand(brandId);
		return ResponseEntity.ok(new ApiResponse<>("Brand Deleted Successfully!", null, HttpStatus.OK));
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllBrands(){
		List<BrandDto> lbrand=bservice.getAllBrand();
		return ResponseEntity.ok(new ApiResponse<>("All Brands!", lbrand, HttpStatus.OK));
	}
	
	@GetMapping("/getbyId/{brandId}")
	public ResponseEntity<?> getbrandById(@PathVariable Integer brandId){
		BrandDto brand=bservice.getByBrandId(brandId);
		return ResponseEntity.ok(new ApiResponse<>("Specific Product!", brand, HttpStatus.OK));
	}
}
