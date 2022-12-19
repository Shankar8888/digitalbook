package com.digitalbook.book.app.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book.app.models.Book;
import com.digitalbook.book.app.response.MasterResponseObject;
import com.digitalbook.book.app.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	
	@GetMapping
	public MasterResponseObject getAllBooks() {
		logger.info("Inside getAllBooks method in BookController");

		return bookService.getAllBooks();
	}
	
	@GetMapping("/{book-id}")
	public MasterResponseObject getBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside getBookById method in BookController");

		return bookService.getBookById(bookId);
	}
	
	@GetMapping("/{author}")
	public MasterResponseObject getBookByAuthor(@PathVariable("author") String authorName) {
		logger.info("Inside getBookById method in BookController");

		return bookService.getBooksByAuthor(authorName);
	}
	
	@DeleteMapping("/{book-id}")
	public MasterResponseObject DeleteBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside DeleteBookById method in BookController");

		return bookService.DeleteBookById(bookId);
	}
	
	@GetMapping("/search")
	public MasterResponseObject searchBook(@RequestParam String title,@RequestParam String author,
			@RequestParam String category,@RequestParam String publisher,@RequestParam Double price) {
		logger.info("Inside searchBook method in BookController");

		return bookService.searchBook(title,author,category,publisher,price);

	}
	
//	@PostMapping(value = "/save" , consumes =  {"multipart/form-data"})
////	@ResponseStatus(code = HttpStatus.CREATED)
//	public MasterResponseObject saveBook(@RequestPart MultipartFile file ,@Valid @RequestPart Book book) throws MethodArgumentNotValidException {
//		logger.info("Inside saveBook method in BookController");
//		Book newBook=null;
//		try {
//			book.setLogo(file.getBytes());
//			return bookService.saveBook(book);
//		}catch(Exception ex){
//			ex.printStackTrace();
//			return new MasterResponseObject("Something went wrong. Requested Book not Saved.",HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping(value = "/save")
//	@ResponseStatus(code = HttpStatus.CREATED)
	public MasterResponseObject saveBook(@Valid @RequestBody Book book) throws MethodArgumentNotValidException {
		logger.info("Inside saveBook method in BookController");
	
		try {
//			book.setLogo(file.getBytes());
			return bookService.saveBook(book);
		}catch(Exception ex){
			ex.printStackTrace();
			return new MasterResponseObject("Something went wrong. Requested Book not Saved.",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Book updates
	@PutMapping("/update")
	public MasterResponseObject updateBook(@Valid @RequestBody Book book,@RequestParam int bookId)  throws MethodArgumentNotValidException{
		logger.info("Inside updateBook method in BookController");
		
			return bookService.updateBook(book,bookId);

	}
	
	//book blocked yes/no updates
	@GetMapping("/update/{book-id}")
	public MasterResponseObject updateForBookBlocked(@PathVariable("book-id") int bookId, @RequestParam("block") boolean isblocked) {
		logger.info("Inside updateForBookBlocked method in BookController");

		return bookService.updateForBookBlocked(bookId,isblocked);
	}
	
	
}
