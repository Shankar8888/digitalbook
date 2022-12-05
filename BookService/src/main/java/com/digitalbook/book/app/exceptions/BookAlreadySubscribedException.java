package com.digitalbook.book.app.exceptions;

public class BookAlreadySubscribedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7334156584165748417L;
	private String message;

	public String getMessage(int userId) {
		return "Requested Book already Subscribed by user: "+userId;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "BookAlreadyExistsException [message= ]";
	
}
}