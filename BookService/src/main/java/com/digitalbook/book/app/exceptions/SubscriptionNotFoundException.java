package com.digitalbook.book.app.exceptions;


public class SubscriptionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 774263431055714572L;
	
	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public String getMessage(int userId) {
		return "No any book subscribed by user:"+userId;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SubscriptionFoundException [message=" + message + "]";
	}
	
}
