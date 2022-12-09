package com.digitalbook.user.app.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbook.user.app.dto.BookDTO;
import com.digitalbook.user.app.dto.BookResponse;
import com.digitalbook.user.app.exception.RequestNotFoundException;
import com.digitalbook.user.app.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${book.api.url}")
	private String bookBaseUrl; 
	
	@Autowired
	RestTemplate restTemplate;
	

	@GetMapping
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> getAllBooks() {
		logger.info("Inside getAllBooks method in UserController");
		BookResponse[] book = restTemplate.getForObject(bookBaseUrl+"/books", BookResponse[].class);
		if(book==null) {
			throw new RequestNotFoundException("Requested book not found..!");
		}
		return ResponseEntity.status(200).body(book);
	}
	
	@GetMapping("/{book-id}")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> getBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside getBookById method in BookController");
		BookResponse book =restTemplate.getForObject(bookBaseUrl+"/books/{book-id}", BookResponse.class,bookId);
		if(book==null) {
			throw new RequestNotFoundException("Requested book not found..!");
		}
		return ResponseEntity.status(200).body(book);
		
	}
	
	@DeleteMapping("/{book-id}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> DeleteBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside DeleteBookById method in BookController");
		BookResponse book =restTemplate.getForObject(bookBaseUrl+"/books/{book-id}", BookResponse.class, bookId);
		if (null == book) {
			throw new RequestNotFoundException("Requested book not Found..!");
		}
		return ResponseEntity.status(200).body(book);
	}
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> searchBook(@RequestParam String title,@RequestParam String author,
			@RequestParam String category,@RequestParam String publisher,@RequestParam double price) {
		logger.info("Inside searchBook method in BookController");

		ResponseEntity<?> books =restTemplate.getForObject(bookBaseUrl+"/books/search?title={title}&author={author}&category={category}&publisher={publisher}&price={price}", ResponseEntity.class,title,author,category,publisher,price);
//		if (null == books) {
//			throw new RequestNotFoundException("Requested book not Found..!");
//		}
		return ResponseEntity.status(200).body(books);
	}
	
	@PostMapping(value = "/saveBooks" , consumes =  {"multipart/form-data"},produces = {"application/json"})
	@PreAuthorize("hasRole('AUTHOR')")
//	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> saveBook(@RequestPart MultipartFile file ,@Valid @RequestPart BookDTO book) throws MethodArgumentNotValidException, IOException {
		logger.info("Inside saveBook method in BookController");
		
		book.setLogo(file.getBytes());
		ResponseEntity<?> savedBook = restTemplate.postForEntity(bookBaseUrl+"/books/save", book , ResponseEntity.class);
		return ResponseEntity.status(200).body(savedBook);
	}
	
	//Book updates
	@PutMapping("/update")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> updateBook(@Valid @RequestBody BookDTO book)  throws MethodArgumentNotValidException{
		logger.info("Inside updateBook method in BookController");
		BookDTO updatedBook=restTemplate.getForObject("url", BookDTO.class);
		return ResponseEntity.status(200).body(updatedBook);
	}
	
	//book blocked yes/no updates
	@PutMapping("/update/{book-id}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> updateForBookBlocked(@PathVariable("book-id") int bookId, @RequestParam("block") boolean isblocked) {
		logger.info("Inside updateForBookBlocked method in BookController");
		restTemplate.getForObject("url", BookDTO.class);
		return ResponseEntity.status(200).build();
	}

}
