package com.ajith.product.model;

public class ProductDTO {

	private String type;
	private String city;
	private String minimumPrice;
	private String maximumPrice;
	private String color;
	private String gbLimit_Minimum;
	private String gbLimitMaximum;

	public ProductDTO() {
	}

	public ProductDTO(String type, String city, String minimumPrice, String maximumPrice, String color,
			String gbLimit_Minimum, String gbLimitMaximum) {
		this.type = type;
		this.city = city;
		this.minimumPrice = minimumPrice;
		this.maximumPrice = maximumPrice;
		this.color = color;
		this.gbLimit_Minimum = gbLimit_Minimum;
		this.gbLimitMaximum = gbLimitMaximum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(String minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public String getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(String maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getGbLimit_Minimum() {
		return gbLimit_Minimum;
	}

	public void setGbLimit_Minimum(String gbLimit_Minimum) {
		this.gbLimit_Minimum = gbLimit_Minimum;
	}

	public String getGbLimitMaximum() {
		return gbLimitMaximum;
	}

	public void setGbLimitMaximum(String gbLimitMaximum) {
		this.gbLimitMaximum = gbLimitMaximum;
	}

	@Override
	public String toString() {
		return "ProductDTO [type=" + type + ", city=" + city + ", minimumPrice=" + minimumPrice + ", maximumPrice="
				+ maximumPrice + ", color=" + color + ", gbLimit_Minimum=" + gbLimit_Minimum + ", gbLimitMaximum="
				+ gbLimitMaximum + "]";
	}

}
