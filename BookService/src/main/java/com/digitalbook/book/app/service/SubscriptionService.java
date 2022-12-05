package com.digitalbook.book.app.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.exceptions.BookAlreadySubscribedException;
import com.digitalbook.book.app.exceptions.BookNotFoundException;
import com.digitalbook.book.app.exceptions.BookSubscriptionNotCancelledException;
import com.digitalbook.book.app.exceptions.SubscriptionNotFoundException;
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
			throw new BookAlreadySubscribedException();
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
			throw new SubscriptionNotFoundException();
		}else {
		    findSubscribedBooks=bookRepo.findAllById(getAllBookIds);
		    if(findSubscribedBooks.size()==0 && getAllBookIds.isEmpty()) {
				throw new BookNotFoundException();	
			}
		}
		return findSubscribedBooks;
	}


	public Book fetchSubscribedBook(int userId, int subscriptionId) {
		Book findBookBySubscrId=null;
		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
		
		if(subscription==null) {
			throw new SubscriptionNotFoundException();
		}else {
			findBookBySubscrId=bookRepo.findById(subscription.getBookId()).get();
		    if(findBookBySubscrId==null) {
				throw new BookNotFoundException();	
			}
		}
		return findBookBySubscrId;
	}


	public Subscription cancelSubscription(int userId, int subscriptionId) {

		Subscription subscrFound=subscrRepo.findSubscriptionOfBook(userId, subscriptionId);
		
		if(subscrFound==null) {
			throw new SubscriptionNotFoundException();
		}else {
			
			long hours = ChronoUnit.HOURS.between(Util.getLocalDateTime(),subscrFound.getCreatedDateTime());
			if(hours <= 24) {
				subscrRepo.delete(subscrFound);
				return subscrFound;
			}else {
				throw new BookSubscriptionNotCancelledException();
			}
			}
	}

}
