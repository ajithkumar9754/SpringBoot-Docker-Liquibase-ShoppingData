package com.ajith.product.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ajith.product.persistence.entity.Product;
import com.ajith.product.persistence.repository.ProductRepository;
import com.ajith.product.persistence.specification.ProductSpecification;
import com.ajith.product.persistence.specification.SearchCriteria;
import com.ajith.product.persistence.specification.SearchOperation;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

	@Resource
	private ProductRepository productRepository;

	private ProductSpecification specification;
	private Product product;

	@Before
	public void before() {
		product = new Product("phone", "red", 0, 710.0, "Karlskrona");
		specification = new ProductSpecification();
		specification.addCriteria(new SearchCriteria("type", "phone", SearchOperation.MATCH));
		productRepository.save(product);

	}

	@Test
	public void whenFindAll_thenReturnProducts() {
		List<Product> products = productRepository.findAll(specification);
		assertNotNull(products);
		assertTrue(products.size() >= 1);

	}

}
