package com.digitalbook.book.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.exceptions.InvalidRequestException;
import com.digitalbook.book.app.exceptions.RequestNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.repository.BookRepository;
import com.digitalbook.book.app.response.BookWithoutContent;
import com.digitalbook.book.app.response.MasterResponseObject;
import com.digitalbook.book.app.util.Util;

import net.bytebuddy.implementation.bind.annotation.Default;


@Service
public class BookService {
	
	private static Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepo;

	
	public MasterResponseObject getAllBooks() {
		logger.info("Inside getAllBooks method in BookService");
		List<Book> books= bookRepo.findAll();
		List<BookWithoutContent> bookResponse=new ArrayList<BookWithoutContent>();
		
//		 if(books.size()==0) {
//			 return new MasterResponseObject("Books not found in record..!",HttpStatus.NOT_FOUND);
//		 }
		 
		for(Book book:books) {
			if(!book.getIsBlocked())
				bookResponse.add(new ModelMapper().map(book,BookWithoutContent.class));
		}
		  return new MasterResponseObject(HttpStatus.OK,bookResponse);
	}

	public MasterResponseObject getBookById(int bookId) {
		logger.info("Inside getBookById method in BookService");
		BookWithoutContent bookResponse=null;
		Optional<Book> book= bookRepo.findById(bookId);
		 if(book.isEmpty()) {
			 return new MasterResponseObject("Books not found in record..!",HttpStatus.NOT_FOUND);
		 }else {
		if(!book.get().getIsBlocked())
			bookResponse=new ModelMapper().map(book.get(),BookWithoutContent.class);
		return new MasterResponseObject(HttpStatus.OK,bookResponse);
		 }
	}
	
	@Transactional
	public MasterResponseObject DeleteBookById(int bookId) {
		 Optional<Book> book=bookRepo.findById(bookId);
		 
		 if(book.isEmpty()) {
			 return new MasterResponseObject("Requested book not found in records..!",HttpStatus.NOT_FOUND);
		 }else {
		 bookRepo.deleteById(bookId);
		 return new MasterResponseObject("Requested book deleted successfully..!",HttpStatus.OK, book.get());
		 }
		 
		 
	}
	
	
	public MasterResponseObject searchBook(String title, String author, String category, String publisher, Double price) {
		logger.info("Inside searchBook method in BookService");
		
			List<BookWithoutContent> bookResponse=new ArrayList();
		    List<Book> bookResult= bookRepo.findBookByFilters(title, author, category, publisher, price);
		    if(bookResult.size()==0) {
		    	 return new MasterResponseObject("No result found for search request..!",HttpStatus.NOT_FOUND);
			 }else {
			
		for(Book book:bookResult) {
			if(!book.getIsBlocked())
				bookResponse.add(new ModelMapper().map(book,BookWithoutContent.class));
			}
		return new MasterResponseObject(HttpStatus.OK,bookResponse);
		}
	}

	@Transactional
	public MasterResponseObject saveBook(Book book) {
		logger.info("Inside saveBook method in BookService");
		int bookFound=bookRepo.findBookByTitleAndAuthor(book.getTitle(),book.getAuthor());
		
		if(bookFound>0) {
			return new MasterResponseObject("Requested Book already Exists with Title:"+book.getTitle()+" and Author:"+book.getAuthor(),HttpStatus.BAD_REQUEST);
		}
	
			book.setIsBlocked(false);
			book.setCreatedDateTime(Util.getLocalDateTime());
			if(book.getPublishedDate()==null) {
				book.setPublishedDate(Util.getLocalDate());
			}
			book=bookRepo.save(book);
			
			return new MasterResponseObject("Book was created with bookId :" +book.getId(),HttpStatus.CREATED);
	}
	
	@Transactional
	public MasterResponseObject updateBook(Book book, int bookId) throws RequestNotFoundException{
		logger.info("Inside updateBook method in BookService");
		
		if(bookId!=0) {
			Optional<Book> bookExists=bookRepo.findById(bookId); 
			if(bookExists.isEmpty()) {
				return new MasterResponseObject("Requested book not found in records..!", HttpStatus.NOT_FOUND);
			}else {
				if(!(bookExists.get().getAuthor().equalsIgnoreCase(book.getAuthor()) && (bookExists.get().getId()==bookId))) {
				return new MasterResponseObject("You can't update this book. because Book id:"+bookId+" is not belongs to you!", HttpStatus.BAD_REQUEST);	
				}
			book= Book.setExistingBookValues(book,bookExists.get());
			}
		}
			book=bookRepo.save(book);
			return new MasterResponseObject("Book Updated Successfully..!",HttpStatus.OK,book);
	}

	@Transactional
	public MasterResponseObject updateForBookBlocked(int bookId, boolean isblocked) throws RequestNotFoundException{
		logger.info("Inside updateForBookBlocked method in BookService");
		Optional<Book> bookExists=bookRepo.findById(bookId); 
		if(bookExists.isEmpty()) {
			return new MasterResponseObject("Requested book not found in records..!", HttpStatus.NOT_FOUND);
		}else {
		bookRepo.updateForBookBlocked(bookId, isblocked);
		if(isblocked) {
		return new MasterResponseObject("Successfully Blocked book for book id: "+bookId, HttpStatus.OK);
		}else {
			return new MasterResponseObject("Successfully UnBlocked book for book id: "+bookId, HttpStatus.OK);	
		}
		}
		
	}

	public MasterResponseObject getBooksByAuthor(String authorName) {
		List<Book> books=bookRepo.getBooksByAuthor(authorName);
		 
		 if(books.size()==1) {
			 return new MasterResponseObject("Books not found in record..!",HttpStatus.OK);
		 }else {
//			bookResponse=new ModelMapper().map(book.get(),BookWithoutContent.class);
		return new MasterResponseObject("found Books",HttpStatus.OK,books);
		 }
	}

}
