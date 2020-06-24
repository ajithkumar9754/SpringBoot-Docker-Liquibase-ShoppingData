package com.ajith.product.exceptions;

public class ProductServiceException extends RuntimeException {

	/**
	 * Product Service exception
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;

	public ProductServiceException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ProductServiceException(String message) {
		super(message);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
