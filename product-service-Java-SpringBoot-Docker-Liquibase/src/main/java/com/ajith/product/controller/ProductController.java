package com.ajith.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajith.product.exceptions.ErrorConstants;
import com.ajith.product.exceptions.ProductServiceException;
import com.ajith.product.model.APIResponse;
import com.ajith.product.model.ProductDTO;
import com.ajith.product.model.ProductResponse;
import com.ajith.product.service.ProductService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiOperation;

/**
 * Rest Controller for Product API
 * 
 * @author Ajith Kumar
 */

@RestController
@CrossOrigin
public class ProductController {

	Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Product details", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> getProducts(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "min-price", required = false) String minimumPrice,
			@RequestParam(value = "max-price", required = false) String maximumPrice,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "gb-limit-min", required = false) String gbLimit_Minimum,
			@RequestParam(value = "gb-limit-max", required = false) String gbLimitMaximum)
			throws ProductServiceException {

		LOGGER.info("Entering service '/products' API service ");

		if (minimumPrice != null && maximumPrice != null) {
			if (Integer.parseInt(minimumPrice) > Integer.parseInt(maximumPrice)) {
				throw new ProductServiceException(ErrorConstants.MINIMUM_SHOULD_BE_LESS_THAN_MAXIMUM);
			}

		}

		if (gbLimit_Minimum != null && gbLimitMaximum != null) {
			if (Integer.parseInt(gbLimit_Minimum) > Integer.parseInt(gbLimitMaximum)) {
				throw new ProductServiceException(ErrorConstants.MINIMUM_SHOULD_BE_LESS_THAN_MAXIMUM);
			}

		}

		ProductDTO productDTO = new ProductDTO(type, city, minimumPrice, maximumPrice, color, gbLimit_Minimum,
				gbLimitMaximum);

		LOGGER.info(" Request Data -> " + productDTO.toString());

		APIResponse apiResponse = new APIResponse();
		List<ProductResponse> productResponseList = productService.getProducts(productDTO);

		if (productResponseList.isEmpty()) {
			LOGGER.error("No products found");
			throw new ProductServiceException(ErrorConstants.PRODUCT_NOT_FOUND);

		}

		apiResponse.setData(productResponseList);
		LOGGER.info("Finished execution of '/products' API service ");

		return ResponseEntity.ok(apiResponse);

	}

}
