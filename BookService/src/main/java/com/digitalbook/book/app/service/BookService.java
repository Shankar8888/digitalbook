package com.digitalbook.book.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.dto.BookResponse;
import com.digitalbook.book.app.exceptions.InvalidRequestException;
import com.digitalbook.book.app.exceptions.RequestNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.repository.BookRepository;
import com.digitalbook.book.app.util.Util;


@Service
public class BookService {
	
	private static Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepo;

	
	public List<BookResponse> getAllBooks() {
		logger.info("Inside getAllBooks method in BookService");
		List<Book> books= bookRepo.findAll();
		List<BookResponse> bookRes=new ArrayList<BookResponse>();
		
		 if(books.size()==0) {
//			 throw new RequestNotFoundException("Requested books not found..!!");
			 return null;
		 }
		 
		for(Book book:books) {
			if(!book.getIsBlocked())
			bookRes.add(new ModelMapper().map(book,BookResponse.class));
		}
		 return bookRes;
	}

	public BookResponse getBookById(int bookId) {
		logger.info("Inside getBookById method in BookService");
		BookResponse bookRes=null;
		Optional<Book> book= bookRepo.findById(bookId);
		 if(book.isEmpty()) {
//			 throw new RequestNotFoundException("Requested book not Found..!");
			 return null;
		 }else {
		if(!book.get().getIsBlocked())
		bookRes=new ModelMapper().map(book.get(),BookResponse.class);
		return bookRes;
		 }
	}
	
	public Book DeleteBookById(int bookId) {
		// TODO Auto-generated method stub
		 Optional<Book> book=bookRepo.findById(bookId);
		 
//		 System.out.println(book);
		 if(book.isEmpty()) {
//			 throw new RequestNotFoundException("Requested book not found..!");
			 return null;
		 }
		 bookRepo.deleteById(bookId);
		 return book.get();
		 
	}
	
	public BookResponse searchBook(boolean blockStatus, String title, String author, String category, String publisher, double price) {
		logger.info("Inside searchBook method in BookService");
//		 String url="http://localhost:8081/book/searchBook";
			BookResponse bookResponse=null;
		    Book bookResult= bookRepo.findBookByFilters(blockStatus,title, author, category, publisher, price);
		    if(bookResult==null) {
//				 throw new RequestNotFoundException("Requested book not found..!");
		    	return bookResponse;
			 }
			if(!bookResult.getIsBlocked())
			bookResponse=new ModelMapper().map(bookResult,BookResponse.class);
			return bookResponse;
	}

	@Transactional
	public Book saveBook(Book book) {
		logger.info("Inside saveBook method in BookService");
		int bookFound=bookRepo.findBookByTitleAndAuthor(book.getTitle(),book.getAuthor());
		
		if(bookFound>0) {
			throw new InvalidRequestException("Requested Book already Exists with Title:"+book.getTitle()+" and Author:"+book.getAuthor());
		}
		if(book.getId()==0) {
			book.setIsBlocked(false);
			book.setCreatedDateTime(Util.getLocalDateTime());
		}
			return bookRepo.save(book);
	}
	
	@Transactional
	public Book updateBook(Book book, int bookId) throws RequestNotFoundException{
		logger.info("Inside updateBook method in BookService");
		
		if(bookId!=0) {
			Optional<Book> bookExists=bookRepo.findById(bookId); 
			if(bookExists.isEmpty()) {
//				 throw new RequestNotFoundException("Requested book not found..!");
				return null;
			}else {
			book= Book.setExistingBookValues(book,bookExists.get());
			}
		}
			return bookRepo.save(book);
	}

	@Transactional
	public void updateForBookBlocked(int bookId, boolean isblocked) throws RequestNotFoundException{
		logger.info("Inside updateForBookBlocked method in BookService");
		bookRepo.updateForBookBlocked(bookId, isblocked);
	}

}
