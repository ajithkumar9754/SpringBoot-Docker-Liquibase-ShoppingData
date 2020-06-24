package com.ajith.product.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product entity class for Product Table
 * 
 * @author Ajith Kumar
 */

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 622990959162522587L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "product_type")
	private String type;
	@Column(name = "color")
	private String color;
	@Column(name = "gb_limit")
	private Integer gbLimit;
	@Column(name = "price")
	private Double price;
	@Column(name = "address")
	private String address;

	public Product() {
	}

	public Product(String type, String color, Integer gbLimit, Double price, String address) {
		super();
		this.type = type;
		this.color = color;
		this.gbLimit = gbLimit;
		this.price = price;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getGbLimit() {
		return gbLimit;
	}

	public void setGbLimit(Integer gbLimit) {
		this.gbLimit = gbLimit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
