package com.digitalbook.book.app.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.exceptions.InvalidRequestException;
import com.digitalbook.book.app.exceptions.RequestNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.models.Subscription;
import com.digitalbook.book.app.repository.BookRepository;
import com.digitalbook.book.app.repository.SubscriptionRepository;
import com.digitalbook.book.app.util.Util;

@Service
public class SubscriptionService {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
	
	@Autowired
	private SubscriptionRepository subscrRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Transactional
	public Subscription subscribeBook(int userId, int bookId) {
		logger.info("Inside subscribeBook method in BookService");
		int bookSubscribed=subscrRepo.findSubscriptionExists(userId,bookId);
		
		if(bookSubscribed>0) {
			throw new InvalidRequestException("Requested Book already Subscribed by user: "+userId);
		}
		if(userId!=0 && bookId!=0) {
			Subscription sub=new Subscription();
			sub.setBookId(bookId);
			sub.setUserId(userId);
			sub.setCreatedDateTime(Util.getLocalDateTime());
			sub=subscrRepo.save(sub);
			return sub;
		}
		return null;
	}


	public List<Book> fetchAllSubscribedBooks(int userId) {
		
		List<Book> findSubscribedBooks=null;
		List<Integer> getAllBookIds=subscrRepo.findAllSubscribedBookIds(userId);
		
		if(getAllBookIds.size()==0 && getAllBookIds.isEmpty()) {
			throw new RequestNotFoundException("Subscribed book not found..!");
		}else {
		    findSubscribedBooks=bookRepo.findAllById(getAllBookIds);
		    if(findSubscribedBooks.size()==0 && getAllBookIds.isEmpty()) {
				throw new RequestNotFoundException("Requested book not found..!");	
			}
		}
		return findSubscribedBooks;
	}


	public Book fetchSubscribedBook(int userId, int subscriptionId) {
		Book findBookBySubscrId=null;
		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
		
		if(subscription==null) {
			throw new RequestNotFoundException("Subscription of book not found..!");
		}else {
			findBookBySubscrId=bookRepo.findById(subscription.getBookId()).get();
		    if(findBookBySubscrId==null) {
				throw new RequestNotFoundException("Requested book not found..!");	
			}
		}
		return findBookBySubscrId;
	}
	
//	public Book fetchSubscribedBookContent(int userId, int subscriptionId) {
//		Book findBookBySubscrId=null;
//		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
//		
//		if(subscription==null) {
//			throw new SubscriptionNotFoundException();
//		}else {
//			findBookBySubscrId=bookRepo.findById(subscription.getBookId()).get();
//		    if(findBookBySubscrId==null) {
//				throw new RequestNotFoundException("Requested book not found..!");	
//			}
//		}
//		return findBookBySubscrId;
//	}
	
	public Book readSubscribedBook(int userId, int subscriptionId) {
		Book findBookBySubscrId=null;
		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
		
		if(subscription==null) {
			throw new RequestNotFoundException("Subscription of book not found..!");
		}else {
			findBookBySubscrId=bookRepo.findById(subscription.getBookId()).get();
		    if(findBookBySubscrId==null) {
				throw new RequestNotFoundException("Requested book not found..!");	
			}
		}
		return findBookBySubscrId;
	}


	public Subscription cancelSubscription(int userId, int subscriptionId) {

		Subscription subscrFound=subscrRepo.findSubscriptionOfBook(userId, subscriptionId);
		
		if(subscrFound==null) {
			throw new RequestNotFoundException("Subscription of book not found..!");
		}else {
			
			long hours = ChronoUnit.HOURS.between(Util.getLocalDateTime(),subscrFound.getCreatedDateTime());
			if(hours <= 24) {
				subscrRepo.delete(subscrFound);
				return subscrFound;
			}else {
				throw new InvalidRequestException("Subscription cancel request not proceeds..!");
			}
			}
	}

}
