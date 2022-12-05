package com.digitalbook.book.app.exceptions;

public class BookSubscriptionNotCancelledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299947783249508081L;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}



	
}
