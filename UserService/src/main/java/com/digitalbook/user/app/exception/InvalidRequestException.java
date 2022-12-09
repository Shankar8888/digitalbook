package com.digitalbook.user.app.exception;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 4454370859473909022L;

	public InvalidRequestException(String message) {
		super(message);
	}

}
