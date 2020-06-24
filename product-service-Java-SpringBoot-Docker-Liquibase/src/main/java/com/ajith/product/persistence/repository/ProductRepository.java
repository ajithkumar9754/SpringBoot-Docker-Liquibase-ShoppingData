package com.ajith.product.persistence.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ajith.product.persistence.entity.Product;


/**
 * Repository class for Product which supports JPA specification
 * 
 * @author Ajith Kumar
 */


public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	
}
