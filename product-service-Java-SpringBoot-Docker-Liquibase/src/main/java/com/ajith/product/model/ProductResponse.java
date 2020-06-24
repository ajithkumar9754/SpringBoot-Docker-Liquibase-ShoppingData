package com.ajith.product.model;

import com.ajith.product.persistence.entity.Product;

public class ProductResponse {

	private String type;  

	private String properties;

	private Double price;

	private String store_address;

	public ProductResponse(String type, String properties, Double price, String store_address) {
		super();
		this.type = type;
		this.properties = properties;
		this.price = price;
		this.store_address = store_address;
	}

	public ProductResponse(Product p) {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

}
