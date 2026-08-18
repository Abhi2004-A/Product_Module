package com.product.service.imp;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.product.exception.ProductException;
import com.product.response.CloudinaryResponse;
import com.product.service.CloudinaryService;

@Service
public class CloudinaryServiceImp implements CloudinaryService{
	
	@Autowired
	private Cloudinary cloud;

	@Override
	public CloudinaryResponse uploadImage(MultipartFile image) {
		CloudinaryResponse response=null;
		try {
		if(image!=null && !image.isEmpty()) {
				Map<?, ?> cloudresult=cloud.uploader().upload(image, ObjectUtils.emptyMap());
				String publicId=cloudresult.get("public_id").toString();
				String imageUrl=cloudresult.get("secure_url").toString();
				response=new CloudinaryResponse(imageUrl, publicId);
		}
			} catch (IOException e) {
				throw new ProductException("Image Upload Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return response;
	}

	@Override
	public void deleteImage(String publicId) {
		
		try {
			Map<?, ?> deleteresult=cloud.uploader().destroy(publicId, ObjectUtils.emptyMap());
			String result=deleteresult.get("result").toString();
			if(!result.equalsIgnoreCase("ok")) {
				throw new ProductException("An Error Ouccered", HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			throw new ProductException("An Error Ouccered to Delete Image", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
