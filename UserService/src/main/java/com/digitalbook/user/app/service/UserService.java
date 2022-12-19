package com.digitalbook.user.app.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.user.app.dto.Book;
import com.digitalbook.user.app.payload.response.MasterResponseObject;

@Service
public class UserService {

	
	@Value("${book.api.url}")
	private String bookBaseUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	public MasterResponseObject searchBook(String title, String author, String category, String publisher, Double price) {
		    return restTemplate.getForObject(bookBaseUrl
					+ "/books/search?title={title}&author={author}&category={category}&publisher={publisher}&price={price}",
					MasterResponseObject.class, title, author, category, publisher, price);
	}

	public MasterResponseObject getAllBooks() {
		return restTemplate.getForObject(bookBaseUrl + "/books", MasterResponseObject.class);
	}

	public MasterResponseObject getBookById(int bookId) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "/books/{book-id}", MasterResponseObject.class, bookId);
	}

	@Transactional
	public MasterResponseObject deleteBookById(int bookId) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "/books/{book-id}", MasterResponseObject.class, bookId);
	}

	@Transactional
	public MasterResponseObject saveBook(@Valid Book book) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject(bookBaseUrl + "/books/save", book, MasterResponseObject.class);
	}

	@Transactional
	public MasterResponseObject updateBook(@Valid Book book, int bookId) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject(bookBaseUrl + "/books/update?bookId="+bookId, book, MasterResponseObject.class);
	}

	public MasterResponseObject bookBlockedUnblocked(int bookId, boolean isblocked) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "/books/update/" + bookId + "?block=" + isblocked, MasterResponseObject.class);
	}

	@Transactional
	public MasterResponseObject subscribeBook(int userId, int bookId) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject(bookBaseUrl + "/reader/" + userId + "/" + bookId, "{}",MasterResponseObject.class);
	}

	public MasterResponseObject fetchAllSubscribedBooks(int userId) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "reader/{user-id}/books", MasterResponseObject.class,userId);
	}

	public MasterResponseObject fetchSubscribedBook(int userId, int subscriptionId) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "reader/{user-id}/books/{subscription-id}",
				MasterResponseObject.class, userId, subscriptionId);
	}

	public MasterResponseObject readSubscribedBook(int userId, int subscriptionId) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(bookBaseUrl + "reader/{user-id}/books/{subscription-id}/read",
				MasterResponseObject.class, userId, subscriptionId);
	}

	public MasterResponseObject cancelSubscription(int userId, int subscriptionId) {
		// TODO Auto-generated method stub
		return  restTemplate.getForObject(bookBaseUrl + "reader/{user-id}/books/{subscription-id}/cancel",
				MasterResponseObject.class, userId, subscriptionId);
	}

	
}
