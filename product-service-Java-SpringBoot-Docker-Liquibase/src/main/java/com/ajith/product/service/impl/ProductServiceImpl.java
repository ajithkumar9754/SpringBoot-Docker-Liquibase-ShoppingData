package com.ajith.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajith.product.exceptions.ProductServiceException;
import com.ajith.product.model.ProductDTO;
import com.ajith.product.model.ProductResponse;
import com.ajith.product.persistence.entity.Product;
import com.ajith.product.persistence.repository.ProductRepository;
import com.ajith.product.persistence.specification.ProductSpecification;
import com.ajith.product.persistence.specification.SearchCriteria;
import com.ajith.product.persistence.specification.SearchOperation;
import com.ajith.product.service.ProductService;

/**
 * Product Service implementation
 * 
 * @author Ajith Kumar
 */

@Service
public class ProductServiceImpl implements ProductService {

	Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ProductResponse> getProducts(ProductDTO productDTO) throws ProductServiceException {

		LOGGER.info("Entering service getProducts in service ");

		ProductSpecification specification = new ProductSpecification();

		if (StringUtils.isNotBlank(productDTO.getType())) {
			specification.addCriteria(new SearchCriteria("type", productDTO.getType(), SearchOperation.MATCH));
		}
		if (StringUtils.isNotBlank(productDTO.getMinimumPrice())) {
			specification.addCriteria(new SearchCriteria("price", Double.parseDouble(productDTO.getMinimumPrice()),
					SearchOperation.GREATER_THAN_EQUAL));
		}
		if (StringUtils.isNotBlank(productDTO.getMaximumPrice())) {
			specification.addCriteria(new SearchCriteria("price", Double.parseDouble(productDTO.getMaximumPrice()),
					SearchOperation.LESS_THAN_EQUAL));
		}
		if (StringUtils.isNotBlank(productDTO.getCity())) {
			specification.addCriteria(new SearchCriteria("address", productDTO.getCity(), SearchOperation.MATCH));
		}
		if (StringUtils.isNotBlank(productDTO.getGbLimit_Minimum())) {
			specification.addCriteria(new SearchCriteria("gbLimit", Integer.parseInt(productDTO.getGbLimit_Minimum()),
					true, SearchOperation.GREATER_THAN_EQUAL));
			specification.addCriteria(new SearchCriteria("type", "subscription", SearchOperation.MATCH));
		}
		if (StringUtils.isNotBlank(productDTO.getGbLimitMaximum())) {
			specification.addCriteria(new SearchCriteria("gbLimit", Integer.parseInt(productDTO.getGbLimitMaximum()),
					true, SearchOperation.LESS_THAN_EQUAL));
			specification.addCriteria(new SearchCriteria("type", "subscription", SearchOperation.MATCH));
		}
		if (StringUtils.isNotBlank(productDTO.getColor())) {
			specification.addCriteria(new SearchCriteria("type", "phone", SearchOperation.MATCH));
			specification.addCriteria(new SearchCriteria("color", productDTO.getColor(), SearchOperation.MATCH));
		}

		List<Product> productList = productRepository.findAll(specification);

		/*
		 * Once product List is recieved we have to convert it into ProductResponse
		 * model list, This conversion will take place here
		 */

		List<ProductResponse> productResponseList = productList.stream().map(product -> {
			String property = product.getType().equals("subscription") ? "gb_limit:" + product.getGbLimit()
					: "color:" + product.getColor();
			return new ProductResponse(product.getType(), property, product.getPrice(), product.getAddress());
		}).collect(Collectors.toList());

		LOGGER.info("Finished execution of getProducts() service ");

		return productResponseList;

	}

}
