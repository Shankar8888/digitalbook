package com.digitalbook.book.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book.app.exceptions.InvalidRequestException;
import com.digitalbook.book.app.exceptions.RequestNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.models.Subscription;
import com.digitalbook.book.app.service.SubscriptionService;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {


	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private SubscriptionService subscrService;
	
	//reader can subscibe book
	@PostMapping(value = "reader/{user-id}/{book-id}")
	public ResponseEntity<?> subscribeBook(@PathVariable("user-id") int userId, @PathVariable("book-id") int bookId)  {
		logger.info("Inside subscribeBook method in SubscriptionController");
		Subscription newSubscription=null;
		try {
			newSubscription = subscrService.subscribeBook(userId,bookId);
		return ResponseEntity.status(201).body(newSubscription);
		}
		catch(InvalidRequestException ex){
			logger.error(ex.getMessage());
			throw new InvalidRequestException("This Book already subscribed by you..!");
		}catch(Exception ex){
			ex.printStackTrace();
			return ResponseEntity.status(500).body("Something went wrong. Requested Book is not subscribed by you..!");
		}
	}
	
	//Reader can fetch all subscribed books
		@GetMapping(value = "reader/{user-id}/books")
		public ResponseEntity<?> fetchAllSubscribedBooks(@PathVariable("user-id") int userId)  {
			logger.info("Inside fetchAllSubscribedBooks method in SubscriptionController");
			List<Book> subscribedBooks=null;
			try {
				subscribedBooks = subscrService.fetchAllSubscribedBooks(userId);
			return ResponseEntity.status(201).body(subscribedBooks);
			}
			catch(RequestNotFoundException ex){
//				ex.getMessage(userId);
			 throw new RequestNotFoundException("Subscription of book not found..!");
			}catch(Exception ex){
				ex.printStackTrace();
				return ResponseEntity.status(500).body("Something went wrong. Current request was not proceeds..!");
			}
		}
	
		//Reader can fetch single subscribed book by id
		@GetMapping(value = "reader/{user-id}/books/{subscription-id}")
		public ResponseEntity<?> fetchSubscribedBook(@PathVariable("user-id") int userId,@PathVariable("subscription-id") int subscriptionId) {
			logger.info("Inside fetchSubscribedBook method in SubscriptionController");

			Book subscribedBook = subscrService.fetchSubscribedBook(userId,subscriptionId);
				if (subscribedBook == null) {
					throw new RequestNotFoundException("Subscription of book not found..!");
				}
			return ResponseEntity.status(200).body(subscribedBook);
		}
		
		//Reader can read subscribed book by id
				@GetMapping(value = "reader/{user-id}/books/{subscription-id}/read")
				public ResponseEntity<?> readSubscribedBook(@PathVariable("user-id") int userId,@PathVariable("subscription-id") int subscriptionId) {
					logger.info("Inside fetchSubscribedBook method in SubscriptionController");

					Book subscribedBook = subscrService.readSubscribedBook(userId,subscriptionId);
						if (subscribedBook == null) {
							throw new RequestNotFoundException("Subscribed book not found..!");
						}
					return ResponseEntity.status(200).body(subscribedBook);
				}
		
		//cancel Subscription within 24 hr
		@GetMapping(value = "reader/{user-id}/books/{subscription-id}/cancel")
		public ResponseEntity<?> cancelSubscription(@PathVariable("user-id") int userId, @PathVariable("subscription-id") int subscriptionId) {
			logger.info("Inside cancelSubscription method in SubscriptionController");

			try {
			Subscription unsubscribedBook = subscrService.cancelSubscription(userId,subscriptionId);
			return ResponseEntity.status(200).body("Successfully Cancel subscription for bookId:"+unsubscribedBook.getBookId());
			}catch (RequestNotFoundException e) {
				throw new RequestNotFoundException("No any book subscribed by you..!");
			}catch (InvalidRequestException ex) {
				ex.getMessage();
				throw new InvalidRequestException("Subscription cancel request not proceeds..!");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.status(500).body("something went wrong..Subscription cancel request not proceeds..!");
			}
		}
		
}
