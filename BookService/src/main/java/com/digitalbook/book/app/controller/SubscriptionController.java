 package com.digitalbook.book.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book.app.response.MasterResponseObject;
import com.digitalbook.book.app.service.SubscriptionService;

@RestController
@RequestMapping("/")
public class SubscriptionController {


	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private SubscriptionService subscrService;
	
	//reader can subscibe book
	@PostMapping(value = "reader/{user-id}/{book-id}")
	public MasterResponseObject subscribeBook(@PathVariable("user-id") int userId, @PathVariable("book-id") int bookId)  {
		logger.info("Inside subscribeBook method in SubscriptionController");
		return subscrService.subscribeBook(userId,bookId);
	}
	
	//Reader can fetch all subscribed books
		@GetMapping(value = "reader/{user-id}/books")
		public MasterResponseObject fetchAllSubscribedBooks(@PathVariable("user-id") int userId)  {
			logger.info("Inside fetchAllSubscribedBooks method in SubscriptionController");
				return subscrService.fetchAllSubscribedBooks(userId);
		}
	
		//Reader can fetch single subscribed book by id
		@GetMapping(value = "reader/{user-id}/books/{subscription-id}")
		public MasterResponseObject fetchSubscribedBook(@PathVariable("user-id") int userId,@PathVariable("subscription-id") int subscriptionId) {
			logger.info("Inside fetchSubscribedBook method in SubscriptionController");

			return subscrService.fetchSubscribedBook(userId,subscriptionId);
	
		}
		
		//Reader can read subscribed book by id
				@GetMapping(value = "reader/{user-id}/books/{subscription-id}/read")
				public MasterResponseObject readSubscribedBook(@PathVariable("user-id") int userId,@PathVariable("subscription-id") int subscriptionId) {
					logger.info("Inside readSubscribedBook method in SubscriptionController");

					return subscrService.readSubscribedBook(userId,subscriptionId);
				}
		
		//cancel Subscription within 24 hr
		@GetMapping(value = "reader/{user-id}/books/{subscription-id}/cancel")
		public MasterResponseObject cancelSubscription(@PathVariable("user-id") int userId, @PathVariable("subscription-id") int subscriptionId) {
			logger.info("Inside cancelSubscription method in SubscriptionController");
			
			return subscrService.cancelSubscription(userId,subscriptionId);
	
		}
		
}
