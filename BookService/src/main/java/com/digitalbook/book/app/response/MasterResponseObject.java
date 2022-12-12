package com.digitalbook.book.app.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.models.Subscription;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterResponseObject {

	private String message;
	private HttpStatus status;
	private List<Book> bookList;
	private List<Subscription> subscriptionList;
	private Book book;
	private Subscription subscription;
	private List<BookWithoutContent> bookResponseList;
	private BookWithoutContent bookResponse;
	

	public MasterResponseObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MasterResponseObject(String message, HttpStatus status, Book book) {
		super();
		
		this.message = message;
		this.status = status;
		this.book = book;
	}
	
	public MasterResponseObject( HttpStatus status,String message, Subscription subscription) {
		super();
		this.status = status;
		this.message = message;
		this.subscription = subscription;
	}
	
	public MasterResponseObject(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	public MasterResponseObject(String message, HttpStatus status, List<Book> bookList) {
		super();
		this.message = message;
		this.status = status;
		this.bookList = bookList;
	}

	public MasterResponseObject(HttpStatus status,String message,List<Subscription> subscriptionList) {
		super();
		this.status = status;
		this.message = message;
		this.subscriptionList = subscriptionList;
	}
	public MasterResponseObject(HttpStatus status,List<BookWithoutContent> bookResponseList) {
		super();
		this.status = status;
		this.bookResponseList=bookResponseList;
	}

	public MasterResponseObject(HttpStatus status,BookWithoutContent bookResponse) {
		super();
		this.status = status;
		this.bookResponse=bookResponse;
	}
	
	public List<BookWithoutContent> getBookResponseList() {
		return bookResponseList;
	}

	public void setBookResponseList(List<BookWithoutContent> bookResponseList) {
		this.bookResponseList = bookResponseList;
	}

	public BookWithoutContent getBookResponse() {
		return bookResponse;
	}

	public void setBookResponse(BookWithoutContent bookResponse) {
		this.bookResponse = bookResponse;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public List<Book> getBookList() {
		return bookList;
	}
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	public List<Subscription> getSubscriptionList() {
		return subscriptionList;
	}
	public void setSubscriptionList(List<Subscription> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
}
