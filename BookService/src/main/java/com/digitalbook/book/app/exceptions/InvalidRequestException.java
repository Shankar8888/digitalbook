package com.digitalbook.book.app.exceptions;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 4454370859473909022L;

	public InvalidRequestException(String message) {
		super(message);
	}

}
