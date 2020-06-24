package com.ajith.product.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ajith.product.ProductApiServiceApplicationTests;
import com.ajith.product.controller.ProductController;
import com.ajith.product.exceptions.ErrorConstants;
import com.ajith.product.exceptions.ProductExceptionHandler;
import com.ajith.product.exceptions.ProductServiceException;
import com.ajith.product.model.ProductResponse;
import com.ajith.product.service.ProductService;

public class ProductControllerTest extends ProductApiServiceApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	List<ProductResponse> productListByCity, productListByColor, productListForMinimumGB, productListForMaximumPrice,
			productListForMaximumGB, productListForPriceRange, productListForGBLimit, productListAll,
			productListForSubscription, productListForMinPrice;

	/*
	 * List all the products by GB limit Maximum
	 */

	@Test
	public void testGetAllProductsWithMaximumGB() throws Exception {

		/*
		 * Added following logic to parse GB size from 'gb_limit:<SOME VALUE>'. Since
		 * this data is only applicable for subscription, first filter with type
		 * 'subscription' and then get parse the property data
		 */

		List<ProductResponse> products = productListForMaximumGB.stream()
				.filter(p -> p.getType().equalsIgnoreCase("subscription"))
				.filter(p -> p.getProperties().contains("gb_limit")).map(p -> {
					String[] props = p.getProperties().split(":");
					return new ProductResponse(p.getType(), props[1], p.getPrice(), p.getStore_address());

				}).filter(p -> (Integer.parseInt(p.getProperties())) <= 50).collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?gb_limit_max=50").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(2)))
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));

	}

	/*
	 * Get all products in between a price range
	 */

	@Test
	public void testGetAllProductsWithin_PriceRange() throws Exception {

		List<ProductResponse> products = productListForPriceRange.stream().filter(p -> p.getPrice() <= 600)
				.filter(p -> p.getPrice() >= 200).collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=phone&min_price=200&max_price=600").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(3)));
	}

	/*
	 * Get all products in between a GB Limit
	 */

	@Test
	public void testGetAllProductsWithin_GBLimit() throws Exception {

		List<ProductResponse> products = productListForGBLimit.stream()
				.filter(p -> p.getType().equalsIgnoreCase("subscription"))
				.filter(p -> p.getProperties().contains("gb_limit")).map(p -> {
					String[] props = p.getProperties().split(":");
					return new ProductResponse(p.getType(), props[1], p.getPrice(), p.getStore_address());

				}).filter(p -> (Integer.parseInt(p.getProperties())) <= 20)
				.filter(p -> (Integer.parseInt(p.getProperties())) >= 10).collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=subscription&gb_limit_min=10&gb_limit_max=20")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$['data']", hasSize(3)));
	}

	/*
	 * Get all products by type 'Subscription' and given city
	 */
	@Test
	public void testGetAllProductsWithSubscriptionAndCity() throws Exception {

		List<ProductResponse> productList = Arrays.asList(
				new ProductResponse("subscription", "gb_limit:10", 99.0, "Stockholm"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"));

		List<ProductResponse> products = productList.stream()
				.filter(p -> p.getType() == "subscription" && p.getStore_address().equals("Stockholm"))
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=subscription&city=Stockholm").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(1)))
				// .andDo(print())
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].properties", is(products.get(0).getProperties())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));
	}

	/*
	 * Get all products by type 'phone' and given city
	 */

	@Test
	public void testGetAllProductsWithPhoneAndCity() throws Exception {

		List<ProductResponse> productList = Arrays.asList(new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"));

		List<ProductResponse> products = productList.stream()
				.filter(p -> p.getType() == "phone" && p.getStore_address().equals("Malmö"))
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=phone&city=Malmö").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(1)))
				// .andDo(print())
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].properties", is(products.get(0).getProperties())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));
	}

	/*
	 * Get all products
	 */

	@Test
	public void testGetAllProducts() throws Exception {

		List<ProductResponse> products = productListAll.stream().collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=phone&city=Malmö").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(5)));
	}

	/*
	 * Get all products when type is Subscription
	 */

	@Test
	public void testGetAllProductsWhenTypeIsSubscription() throws Exception {

		List<ProductResponse> products = productListForSubscription.stream().filter(x -> x.getType() == "subscription")
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=subscription").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(4)));

	}

	/*
	 * Check if Product is available or not
	 */

	@Test
	public void testProductNotFound() throws Exception {

		given(productService.getProducts(Mockito.any())).willThrow(
				new ProductServiceException(ErrorConstants.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST.value()));

		mockMvc.perform(get("/product?type=phone&city=Malmö").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(ErrorConstants.PRODUCT_NOT_FOUND)))
				.andExpect(jsonPath("$.details", is("uri=/product")));
	}

	/*
	 * List all the products with price less than given amount
	 */

	@Test
	public void testGetAllProductsWithMinPrice() throws Exception {

		List<ProductResponse> products = productListForMinPrice.stream().filter(p -> p.getPrice() <= 500.0)
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?min_price=500").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(3)))
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].properties", is(products.get(0).getProperties())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));

	}

	/*
	 * List all the products with price is more than given amount
	 */

	@Test
	public void testGetAllProductsWithMaximumPrice() throws Exception {

		List<ProductResponse> products = productListForMaximumPrice.stream().filter(p -> p.getPrice() >= 500.0)
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?min_price=500").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(2)))
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].properties", is(products.get(0).getProperties())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));

	}

	/*
	 * List all the products by GB limit Minimum
	 */

	@Test
	public void testGetAllProductsWithMinimumGB() throws Exception {

		/*
		 * Added following logic to parse GB size from 'gb_limit:50'. Since this data is
		 * only applicable for subscription, first filter with type 'subscription' and
		 * then get parse the property data
		 */

		List<ProductResponse> products = productListForMinimumGB.stream()
				.filter(p -> p.getType().equalsIgnoreCase("subscription"))
				.filter(p -> p.getProperties().contains("gb_limit")).map(p -> {
					String[] props = p.getProperties().split(":");
					return new ProductResponse(p.getType(), props[1], p.getPrice(), p.getStore_address());

				}).filter(p -> (Integer.parseInt(p.getProperties())) >= 50).collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?gb_limit_max=50").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(3)))
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));

	}

	/*
	 * Get all products by given color
	 */

	@Test
	public void testGetAllProductsWithColor() throws Exception {

		List<ProductResponse> products = productListByColor.stream().filter(p -> p.getType().equalsIgnoreCase("phone"))
				.filter(p -> p.getProperties().contains("color")).map(p -> {
					String[] props = p.getProperties().split(":");
					return new ProductResponse(p.getType(), props[1], p.getPrice(), p.getStore_address());

				}).filter(p -> p.getProperties().equalsIgnoreCase("rosa")).collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?type=phone&color=rosa").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$['data']", hasSize(2)))
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));

	}

	/*
	 * Get all products by given city
	 */

	@Test
	public void testGetAllProductsByCity() throws Exception {

		List<ProductResponse> products = productListByCity.stream().filter(p -> p.getStore_address().equals("Malmö"))
				.collect(Collectors.toList());

		given(productService.getProducts(Mockito.any())).willReturn(products);

		mockMvc.perform(get("/product?city=Malmö").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$['data']", hasSize(5)))
				// .andDo(print())
				.andExpect(jsonPath("$['data'][0].type", is(products.get(0).getType())))
				.andExpect(jsonPath("$['data'][0].properties", is(products.get(0).getProperties())))
				.andExpect(jsonPath("$['data'][0].price", is(products.get(0).getPrice())))
				.andExpect(jsonPath("$['data'][0].store_address", is(products.get(0).getStore_address())));
	}

	@Before
	public void init() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.setControllerAdvice(new ProductExceptionHandler()).build();

		productListByCity = Arrays.asList(new ProductResponse("subscription", "gb_limit:81", 202.0, "Malmö"),
				new ProductResponse("phone", "color:rosa", 499.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 455.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:150", 474.0, "Malmö"),
				new ProductResponse("phone", "color:rosa", 100.0, "Malmö"));

		productListByColor = Arrays.asList(new ProductResponse("subscription", "gb_limit:81", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 499.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 455.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:150", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:rosa", 100.0, "Malmö"));

		productListForMinimumGB = Arrays.asList(new ProductResponse("subscription", "gb_limit:81", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 499.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 455.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:150", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 100.0, "Malmö"));

		productListForMaximumPrice = Arrays.asList(
				new ProductResponse("subscription", "gb_limit:10", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 499.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 455.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:50", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 100.0, "Malmö"));

		productListForMaximumGB = Arrays.asList(new ProductResponse("subscription", "gb_limit:51", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 499.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 455.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:150", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 100.0, "Malmö"));

		productListForPriceRange = Arrays.asList(new ProductResponse("phone", "color:guld", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:guld", 400.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"));

		productListForGBLimit = Arrays.asList(new ProductResponse("subscription", "gb_limit:10", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:15", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:18", 130.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:50", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"));

		productListAll = Arrays.asList(new ProductResponse("subscription", "gb_limit:10", 202.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"));

		productListForSubscription = Arrays.asList(
				new ProductResponse("subscription", "gb_limit:10", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 130.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:50", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"));

		productListForMinPrice = Arrays.asList(new ProductResponse("subscription", "gb_limit:10", 202.0, "Karlskrona"),
				new ProductResponse("phone", "color:rosa", 599.0, "Stockholm"),
				new ProductResponse("subscription", "gb_limit:10", 599.0, "Malmö"),
				new ProductResponse("phone", "color:guld", 45.0, "Malmö"),
				new ProductResponse("subscription", "gb_limit:50", 1000.0, "Karlskrona"),
				new ProductResponse("subscription", "gb_limit:50", 474.0, "Stockholm"),
				new ProductResponse("phone", "color:guld", 945.0, "Malmö"));

	}

}
