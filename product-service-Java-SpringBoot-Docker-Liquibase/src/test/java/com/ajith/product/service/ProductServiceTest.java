package com.ajith.product.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ajith.product.ProductApiServiceApplicationTests;
import com.ajith.product.exceptions.ProductServiceException;
import com.ajith.product.model.ProductDTO;
import com.ajith.product.model.ProductResponse;
import com.ajith.product.persistence.entity.Product;
import com.ajith.product.persistence.repository.ProductRepository;
import com.ajith.product.service.impl.ProductServiceImpl;

public class ProductServiceTest extends ProductApiServiceApplicationTests {

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Mock
	private ProductRepository productRepository;

	private List<Product> productList;

	@Before
	public void init() {

		productList = Arrays.asList(new Product("subscription", null, 50, 327.0, "Malmö"),
				new Product("subscription", null, 89, 987.0, "Stockholm"),
				new Product("subscription", null, 18, 750.0, "Karlskrona"),
				new Product("phone", "guld", 0, 346.0, "Stockholm"), new Product("phone", "black", 0, 945.0, "Malmö"),
				new Product("phone", "red", 0, 710.0, "Karlskrona"),
				new Product("phone", "red", 0, 999.0, "Karlskrona"));
	}

	/*
	 * 
	 * Get all products by type 'Subscription' and given city
	 */
	@Test
	public void testGetAllProductsWithSubscriptionAndCity() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setType("subscription");
		product.setCity("Stockholm");

		List<Product> products = productList.stream()
				.filter(p -> p.getType().equals("subscription") && p.getAddress().equals("Stockholm"))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(1, is(response.size()));
		assertEquals("subscription", response.get(0).getType());

	}

	/*
	 * 
	 * Get all products by type 'Phone' and given city
	 */
	@Test
	public void testGetAllProductsWithPhoneAndCity() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setType("phone");
		product.setCity("Karlskrona");

		List<Product> products = productList.stream()
				.filter(p -> p.getType().equals("phone") && p.getAddress().equals("Karlskrona"))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(2, is(response.size()));
		assertEquals("phone", response.get(0).getType());

	}

	/*
	 * 
	 * Get all products
	 */
	@Test
	public void testGetAllProducts() throws ProductServiceException {

		ProductDTO product = new ProductDTO();

		List<Product> products = productList.stream().collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(7, is(response.size()));

	}

	/*
	 * 
	 * Get all products when type is subscription
	 */
	@Test
	public void testGetAllProductsWhenTypeIsSubscription() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setType("subscription");

		List<Product> products = productList.stream().filter(p -> p.getType().equals("subscription"))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(3, is(response.size()));
		assertEquals("subscription", response.get(0).getType());

	}

	/*
	 * 
	 * Get all products with price less than given range
	 */
	@Test
	public void testGetAllProductsWithMinPrice() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setMinimumPrice("500");

		List<Product> products = productList.stream().filter(p -> p.getPrice() < 500.0).collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(2, is(response.size()));

	}

	/*
	 * 
	 * Get all products with price above given range
	 */
	@Test
	public void testGetAllProductsWithMaximumPrice() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setMinimumPrice("500");

		List<Product> products = productList.stream().filter(p -> p.getPrice() > 500.0).collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(5, is(response.size()));

	}

	/*
	 * 
	 * Get all products by type 'Subscription'
	 */
	@Test
	public void testGetAllProductsWithSubscription() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setType("subscription");

		List<Product> products = productList.stream().filter(p -> p.getType().equals("subscription"))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(3, is(response.size()));
		assertEquals("subscription", response.get(0).getType());

	}

	/*
	 * 
	 * Get all products by type 'Phone'
	 */
	@Test
	public void testGetAllProductsWithPhone() throws ProductServiceException {

		ProductDTO product = new ProductDTO();
		product.setType("phone");

		List<Product> products = productList.stream().filter(p -> p.getType().equals("phone"))
				.collect(Collectors.toList());

		Mockito.when(productRepository.findAll(Mockito.any())).thenReturn(products);

		List<ProductResponse> response = productServiceImpl.getProducts(product);
		assertNotNull(response);
		assertThat(4, is(response.size()));
		assertEquals("phone", response.get(0).getType());

	}

}
