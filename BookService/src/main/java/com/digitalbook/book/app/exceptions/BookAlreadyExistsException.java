package com.digitalbook.book.app.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;

public class BookAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String message;

	public String getMessage(String t, String a) {
		return "Requested Book already Exists with Title: "+t+" and Author: "+a ;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "BookAlreadyExistsException [message= ]";
	}
}
