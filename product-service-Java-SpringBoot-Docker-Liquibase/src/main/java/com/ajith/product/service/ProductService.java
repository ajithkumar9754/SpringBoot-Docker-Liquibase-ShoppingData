package com.ajith.product.service;

import java.util.List;

import com.ajith.product.exceptions.ProductServiceException;
import com.ajith.product.model.ProductDTO;
import com.ajith.product.model.ProductResponse;

public interface ProductService {

	List<ProductResponse>   getProducts(ProductDTO productDTO) throws ProductServiceException;

}
