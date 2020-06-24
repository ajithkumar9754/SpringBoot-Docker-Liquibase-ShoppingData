package com.ajith.product.persistence.specification;

/**
 * Search Criteria implementation
 * 
 * @author Ajith Kumar
 */

public class SearchCriteria {

	private String key;
	private Object value;
	private SearchOperation operation;
	private Boolean isNumericValue;

	public SearchCriteria() {
	}

	public SearchCriteria(String key, Object value, SearchOperation operation) {
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

	public SearchCriteria(String key, Object value, Boolean isNumericValue, SearchOperation operation) {
		this.key = key;
		this.value = value;
		this.operation = operation;
		this.isNumericValue = isNumericValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchOperation getOperation() {
		return operation;
	}

	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}

	public Boolean getIsNumericValue() {
		return isNumericValue;
	}

	public void setIsNumericValue(Boolean isNumericValue) {
		this.isNumericValue = isNumericValue;
	}

}
