package com.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.response.ApiResponse;

@RestControllerAdvice
public class GlobalProductexception {
	
	@ExceptionHandler(exception = ProductException.class)
	public ResponseEntity<?> globalhandler(ProductException exception){
		return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(),null,exception.gethttpStatus()),exception.gethttpStatus());
	}

}
