package com.digitalbook.book.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbook.book.app.dto.BookResponse;
import com.digitalbook.book.app.exceptions.InvalidRequestException;
import com.digitalbook.book.app.exceptions.RequestNotFoundException;
import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	
	@GetMapping
	public ResponseEntity<?> getAllBooks() {
		logger.info("Inside getAllBooks method in BookController");

		List<BookResponse> book =bookService.getAllBooks();
//			if (book.size()==0) {
//				throw new RequestNotFoundException("Requested Book not Found..!");
//			}
		return ResponseEntity.status(200).body(book);
	}
	
	@GetMapping("/{book-id}")
	public ResponseEntity<?> getBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside getBookById method in BookController");

		BookResponse book = bookService.getBookById(bookId);
		System.out.println(book);
//			if (null == book) {
//				throw new RequestNotFoundException("Requested Book not Found..!");
//			}
		return ResponseEntity.status(200).body(book);
	}
	
	@DeleteMapping("/{book-id}")
	public ResponseEntity<?> DeleteBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside DeleteBookById method in BookController");

		Book book = bookService.DeleteBookById(bookId);
//			if (null == book) {
//				throw new RequestNotFoundException("Requested book not Found..!");
//			}
		return ResponseEntity.status(200).body(book);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchBook(@RequestParam String title,@RequestParam String author,
			@RequestParam String category,@RequestParam String publisher,@RequestParam double price) {
		logger.info("Inside searchBook method in BookController");

		BookResponse book = bookService.searchBook(false,title,author,category,publisher,price);
//			if (null == book) {
//				throw new RequestNotFoundException("Requested book not found..!");
//			}
		return ResponseEntity.status(200).body(book);
	}
	
	@PostMapping(value = "/save" , consumes =  {"multipart/form-data"})
//	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> saveBook(@RequestPart MultipartFile file ,@Valid @RequestPart Book book) throws MethodArgumentNotValidException {
		logger.info("Inside saveBook method in BookController");
		Book newBook=null;
		try {
			book.setLogo(file.getBytes());
			newBook=bookService.saveBook(book);
		return ResponseEntity.status(201).body(newBook);
		}
		catch(InvalidRequestException ex){
			logger.error(ex.getMessage());
			throw new InvalidRequestException("Requested Book already Exists with Title:"+book.getTitle()+" and Author:"+book.getAuthor());
		}catch(Exception ex){
			ex.printStackTrace();
			return ResponseEntity.status(500).body("Something went wrong. Requested Book not Saved.");
		}
	}
	
	//Book updates
	@PutMapping("/update")
	public ResponseEntity<?> updateBook(@Valid @RequestBody Book book,@RequestParam("book-id") int bookId)  throws MethodArgumentNotValidException{
		logger.info("Inside updateBook method in BookController");
		Book updatedBook=null;
		try {
			updatedBook=bookService.updateBook(book,bookId);
		return ResponseEntity.status(200).body(updatedBook);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(500).body("Something went wrong. Requested Book not updated.");
//			return ResponseEntity.status(500).body(new Messager  updatedBook);
		}
	}
	
	//book blocked yes/no updates
	@PutMapping("/update/{book-id}")
	public ResponseEntity<?> updateForBookBlocked(@PathVariable("book-id") int bookId, @RequestParam("block") boolean isblocked) {
		logger.info("Inside updateForBookBlocked method in BookController");
//		Book updatedBook=null;
		try {
			bookService.updateForBookBlocked(bookId,isblocked);
		return ResponseEntity.status(200).body("Successfully Blocked book for book id: "+bookId);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(500).body("Can't able to Blocked book for book id: "+bookId);
		}
	}
	
	
}
