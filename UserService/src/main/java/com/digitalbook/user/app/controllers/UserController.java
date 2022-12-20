package com.digitalbook.user.app.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.user.app.dto.Book;
import com.digitalbook.user.app.payload.response.MasterResponseObject;
import com.digitalbook.user.app.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> getAllBooks() {
		logger.info("Inside getAllBooks method in UserController");

		MasterResponseObject response = null;
		try {
			response = userService.getAllBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/{book-id}")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> getBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside getBookById method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.getBookById(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	@DeleteMapping("/{book-id}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> DeleteBookById(@PathVariable("book-id") int bookId) {
		logger.info("Inside DeleteBookById method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.deleteBookById(bookId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.getCause());
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/search")
//	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> searchBook(@RequestParam String title, @RequestParam String author,
			@RequestParam String category, @RequestParam String publisher, @RequestParam Double price) {
		logger.info("Inside searchBook method in BookController");
		MasterResponseObject response = null;
		try {
			response = userService.searchBook(title, author, category, publisher, price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

//	@PostMapping(value = "/saveBooks", consumes = { "multipart/form-data" }, produces = { "application/json" })
//	@PreAuthorize("hasRole('AUTHOR')")
//	public ResponseEntity<?> saveBook(@RequestPart MultipartFile file, @Valid @ModelAttribute Book book)
//			throws MethodArgumentNotValidException, IOException {
//		logger.info("Inside saveBook method in UserController");
//		MasterResponseObject response = null;
//		try {
//			book.setLogo(file.getBytes());
//			response = restTemplate.postForObject(bookBaseUrl + "/books/save", book, MasterResponseObject.class);
//			return ResponseEntity.status(response.getStatus()).body(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}

	// save book
	@PostMapping(value = "/save")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> saveBook(@Valid @RequestBody Book book)
			throws MethodArgumentNotValidException, IOException {
		logger.info("Inside saveBook method in UserController");
		MasterResponseObject response = null;
		try {
//			book.setLogo(file.getBytes());
			response = userService.saveBook(book);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(response.getStatus()).body(e.getLocalizedMessage());
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	// Update book
	@PutMapping("/update")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> updateBook(@Valid @RequestBody Book book, @RequestParam int bookId)
			throws MethodArgumentNotValidException {
		logger.info("Inside updateBook method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.updateBook(book, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	// book blocked yes/no updates
	@GetMapping("/update/{book-id}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> bookBlockedUnblocked(@PathVariable("book-id") int bookId,
			@RequestParam("block") boolean isblocked) {
		logger.info("Inside updateForBookBlocked method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.bookBlockedUnblocked(bookId, isblocked);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	//////////// subscription api----------

	// reader can subscibe book
	@PreAuthorize("hasRole('READER')")
	@PostMapping(value = "reader/{user-id}/{book-id}")
	public ResponseEntity<MasterResponseObject> subscribeBook(@PathVariable("user-id") int userId,
			@PathVariable("book-id") int bookId) {
		logger.info("Inside subscribeBook method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.subscribeBook(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	// Reader can fetch all subscribed books
	@PreAuthorize("hasRole('READER')")
	@GetMapping(value = "reader/{user-id}/books")
	public ResponseEntity<MasterResponseObject> fetchAllSubscribedBooks(@PathVariable("user-id") int userId) {
		logger.info("Inside fetchAllSubscribedBooks method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.fetchAllSubscribedBooks(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	// Reader can fetch single subscribed book by id
	@PreAuthorize("hasRole('READER')")
	@GetMapping(value = "reader/{user-id}/books/{subscription-id}")
	public ResponseEntity<MasterResponseObject> fetchSubscribedBook(@PathVariable("user-id") int userId,
			@PathVariable("subscription-id") int subscriptionId) {
		logger.info("Inside fetchSubscribedBook method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.fetchSubscribedBook(userId, subscriptionId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(response.getStatus()).body(response);

	}

	// Reader can read subscribed book by id
	@PreAuthorize("hasRole('READER')")
	@GetMapping(value = "reader/{user-id}/books/{subscription-id}/read")
	public ResponseEntity<MasterResponseObject> readSubscribedBook(@PathVariable("user-id") int userId,
			@PathVariable("subscription-id") int subscriptionId) {
		logger.info("Inside readSubscribedBook method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.readSubscribedBook(userId, subscriptionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	// cancel Subscription within 24 hr
	@PreAuthorize("hasRole('READER')")
	@GetMapping(value = "reader/{user-id}/books/{subscription-id}/cancel")
	public ResponseEntity<MasterResponseObject> cancelSubscription(@PathVariable("user-id") int userId,
			@PathVariable("subscription-id") int subscriptionId) {
		logger.info("Inside cancelSubscription method in UserController");
		MasterResponseObject response = null;
		try {
			response = userService.cancelSubscription(userId, subscriptionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(response.getStatus()).body(response);

	}

}
