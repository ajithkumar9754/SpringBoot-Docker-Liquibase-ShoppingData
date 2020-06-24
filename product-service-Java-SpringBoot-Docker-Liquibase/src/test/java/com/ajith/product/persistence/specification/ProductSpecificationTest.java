package com.ajith.product.persistence.specification;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ajith.product.persistence.entity.Product;
import com.ajith.product.persistence.repository.ProductRepository;
import com.ajith.product.persistence.specification.ProductSpecification;
import com.ajith.product.persistence.specification.SearchCriteria;
import com.ajith.product.persistence.specification.SearchOperation;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductSpecificationTest {

	@Mock
	private ProductRepository productRepository;

	private ProductSpecification specification;

	private SearchCriteria criteria;

	private Product product;

	@Before
	public void init() {
		product = new Product("phone", "red", 0, 710.0, "Karlskrona");
		productRepository.save(product);

	}

	@Test
	public void testProductCriteriaWithTypeIsPhone() {
		specification = new ProductSpecification();
		criteria = new SearchCriteria("type", "phone", SearchOperation.MATCH);
		specification.addCriteria(criteria);
		List<Product> products = productRepository.findAll(specification);
		assertNotNull(products);

	}

}
