package com.digitalbook.book.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.book.app.exceptions.BookAlreadyExistsException;
import com.digitalbook.book.app.exceptions.BookNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.repository.BookRepository;
import com.digitalbook.book.app.util.Util;


@Service
public class BookService {
	
	private static Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepo;

	
	public List<Book> getAllBooks() {
		logger.info("Inside getAllBooks method in BookService");
		return bookRepo.findAll();
	}

	public Book getBookById(int bookId) {
		logger.info("Inside getBookById method in BookService");
		return bookRepo.findById(bookId).get();
	}
	
	public Book DeleteBookById(int bookId) {
		// TODO Auto-generated method stub
		 Optional<Book> book=bookRepo.findById(bookId);
		 
//		 System.out.println(book);
		 if(book.isEmpty()) {
			 throw new BookNotFoundException();
		 }
		 bookRepo.deleteById(bookId);
		 return book.get();
		 
	}
	
	public Book searchBook(boolean blockStatus, String title, String author, String category, String publisher, double price) {
		logger.info("Inside searchBook method in BookService");
//		 String url="http://localhost:8081/book/searchBook";
		 
		    Book bookResult= bookRepo.findBookByFilters(blockStatus,title, author, category, publisher, price);
		    return bookResult;
	}

	@Transactional
	public Book saveBook(Book book) {
		logger.info("Inside saveBook method in BookService");
		int bookFound=bookRepo.findBookByTitleAndAuthor(book.getTitle(),book.getAuthor());
		
		if(bookFound>0) {
			throw new BookAlreadyExistsException();
		}
		if(book.getId()==0) {
			book.setIsBlocked(false);
			book.setCreatedDateTime(Util.getLocalDateTime());
		}
			return bookRepo.save(book);
	}
	
	@Transactional
	public Book updateBook(Book book) throws BookNotFoundException{
		logger.info("Inside updateBook method in BookService");
		
		if(book.getId()!=0) {
			Book bookExists=bookRepo.findById(book.getId()).get();
			book= Book.existingBookVal(book,bookExists);
		}
			return bookRepo.save(book);
	}

	@Transactional
	public void updateForBookBlocked(int bookId, boolean isblocked) throws BookNotFoundException{
		logger.info("Inside updateForBookBlocked method in BookService");
		bookRepo.updateForBookBlocked(bookId, isblocked);
	}

}
