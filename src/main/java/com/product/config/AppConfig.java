package com.product.config;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class AppConfig {
	
	@Value("${cloudinary.cloud-name}")
	private String cloud_name;
	
	@Value("${cloudinary.api-key}")
	private String api_key;
	
	@Value("${cloudinary.api-secret}")
	private String api_secret;
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Cloudinary cloudConfig() {
		Map<String, Object> cloud=new HashMap<>();
		cloud.put("cloud_name", cloud_name);
		cloud.put("api_key", api_key);
		cloud.put("api_secret", api_secret);
		return new Cloudinary(cloud);
	}

}
