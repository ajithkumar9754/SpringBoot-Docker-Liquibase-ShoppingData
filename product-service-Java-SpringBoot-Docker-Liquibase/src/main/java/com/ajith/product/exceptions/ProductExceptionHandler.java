package com.ajith.product.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * Centralized exception Handler for Product Service 
 * 
 * @author Ajith Kumar
 */



@ControllerAdvice
public class ProductExceptionHandler {

	@ExceptionHandler(value = { ProductServiceException.class })
	public ResponseEntity<ApiError> handleProductServiceException(ProductServiceException productException,
			WebRequest request) {

		ApiError apiError = new ApiError(productException.getMessage(), request.getDescription(false), new Date());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	
	/*
	 * 
	 * EXCEPTION HANDLER FOR ALL OTHER EXCEPTIONS BESIDES THOSE EXPLICITLY HANDLED
	 * ABOVE
	 */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(ex.getMessage(), request.getDescription(false), new Date());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
