package com.digitalbook.book.app.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.models.Subscription;
import com.digitalbook.book.app.repository.BookRepository;
import com.digitalbook.book.app.repository.SubscriptionRepository;
import com.digitalbook.book.app.response.MasterResponseObject;
import com.digitalbook.book.app.util.Util;

@Service
public class SubscriptionService {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
	
	@Autowired
	private SubscriptionRepository subscrRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Transactional
	public MasterResponseObject subscribeBook(int userId, int bookId) {
		logger.info("Inside subscribeBook method in BookService");
		Optional<Book> bookExist=bookRepo.findById(bookId);
		if(bookExist.isEmpty()) {
			return new MasterResponseObject("Book Not found in record..!",HttpStatus.NOT_FOUND);		
		}else {
			if(bookExist.get().getIsBlocked())
			return new MasterResponseObject("Subscription requested Book Blocked by Author..!",HttpStatus.BAD_REQUEST);	
		}
		int bookSubscribed=subscrRepo.findSubscriptionExists(userId,bookId);
		
		if(bookSubscribed>0) {
			return new MasterResponseObject("Requested Book already Subscribed by user: "+userId,HttpStatus.BAD_REQUEST);	
		}
		
		Subscription subcrption=new Subscription();
		
			subcrption.setBook(bookExist.get());
			subcrption.setUserId(userId);
			subcrption.setCancelled(false);
			subcrption.setSubscriptionDate(Util.getLocalDateTime());
			subcrption=subscrRepo.save(subcrption);
		return new MasterResponseObject("Book subscribed with SubscriptionId: "+subcrption.getId(),HttpStatus.CREATED);
	}


	public MasterResponseObject fetchAllSubscribedBooks(int userId) {
		logger.info("Inside fetchAllSubscribedBooks method in SubscriptionService");
		List<Subscription> findSubscrUnblockedBooks=null;
		List<Subscription> getAllSubscriptions=subscrRepo.findAllSubscribedBookIds(userId);
		
		if(getAllSubscriptions.size()==0 && getAllSubscriptions.isEmpty()) {
//			throw new RequestNotFoundException("Subscribed book not found..!");
			return new MasterResponseObject("Book Subscription not found for current user..!",HttpStatus.NOT_FOUND);
		}
		else {
//			System.out.println(getAllBookIds);
//		    findSubscribedBooks=bookRepo.findAllBooksByIds(getAllBookIds);
			findSubscrUnblockedBooks=getAllSubscriptions.stream().filter(a->a.getBook().getIsBlocked()==false).collect(Collectors.toList());
		    if(findSubscrUnblockedBooks.size()==0) {
////				throw new RequestNotFoundException("Requested book not found..!");	
				return new MasterResponseObject("Requested subscribed book either not found or blocked in records..!",HttpStatus.BAD_REQUEST);
			}
		}
		return new MasterResponseObject(findSubscrUnblockedBooks,HttpStatus.OK);
	}


	public MasterResponseObject fetchSubscribedBook(int userId, int subscriptionId) {
		logger.info("Inside fetchSubscribedBook method in SubscriptionService");
		Book findBookBySubscrId=null;
		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
		
		if(subscription==null) {
//			throw new RequestNotFoundException("Subscription of book not found..!");
			return new MasterResponseObject("Subscription of book not found..!",HttpStatus.NOT_FOUND);
		}else {
			findBookBySubscrId=bookRepo.findById(subscription.getBook().getId()).get();
		    if(findBookBySubscrId==null) {
//				throw new RequestNotFoundException("Requested book not found..!");	
		    	return new MasterResponseObject("Book not found in record..!",HttpStatus.NOT_FOUND);
			}else {
				if(findBookBySubscrId.getIsBlocked())
				return new MasterResponseObject("Requested subscribed book blocked by author..!",HttpStatus.BAD_REQUEST);	
			}
		}
		return new MasterResponseObject("Found record for subscription Id: "+subscriptionId,HttpStatus.OK,findBookBySubscrId);
	}

	public MasterResponseObject readSubscribedBook(int userId, int subscriptionId) {
		logger.info("Inside readSubscribedBook method in SubscriptionService");
		Book findBookBySubscrId=null;
		Subscription subscription=subscrRepo.findSubscriptionOfBook(userId,subscriptionId);
		
		if(subscription==null) {
//			throw new RequestNotFoundException("Subscription of book not found..!");
			return new MasterResponseObject("Subscription of book not found..!",HttpStatus.NOT_FOUND);
		}else {
			findBookBySubscrId = bookRepo.findById(subscription.getBook().getId()).get();
		    if(findBookBySubscrId==null) {
//				throw new RequestNotFoundException("Requested book not found..!");	
				return new MasterResponseObject("Requested book not found in records..!",HttpStatus.NOT_FOUND);
			}else {
				if(findBookBySubscrId.getIsBlocked())
				return new MasterResponseObject("Requested subscribedId: "+subscriptionId+" blocked by author..!",HttpStatus.BAD_REQUEST);	
			}
		}
		return new MasterResponseObject("Found book for reading",HttpStatus.OK,findBookBySubscrId);
	}


	@Transactional
	public MasterResponseObject cancelSubscription(int userId, int subscriptionId) {
		logger.info("Inside cancelSubscription method in SubscriptionService");
		Subscription subscrFound=subscrRepo.findSubscriptionOfBook(userId, subscriptionId);
		
		if(subscrFound==null) {
			return new MasterResponseObject("SubscriptionId: "+subscriptionId+" not found in record..!",HttpStatus.NOT_FOUND);
//			throw new RequestNotFoundException();
		}else {
			
			long hours = ChronoUnit.HOURS.between(Util.getLocalDateTime(),subscrFound.getSubscriptionDate());
			if(hours <= 24) {
				subscrFound.setCancelled(true);
				subscrFound.setDateOfCancellation(Util.getLocalDateTime());
				subscrRepo.save(subscrFound);
				return new MasterResponseObject("Subscription cancel for SubscrptionId: "+subscriptionId,HttpStatus.OK);
			}else {
//				throw new InvalidRequestException("Subscription cancel request not proceeds..!");
				return new MasterResponseObject("Subscription cancellation duration is over..!",HttpStatus.BAD_REQUEST);
			}
			}
	}

}
