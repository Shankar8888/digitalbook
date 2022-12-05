package com.digitalbook.user.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.user.app.payload.response.MessageResponse;
import com.digitalbook.user.app.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('GUEST')")
	public ResponseEntity<?> searchBook(@RequestParam String title,@RequestParam String author,
			@RequestParam String category,@RequestParam String publisher,@RequestParam double price) {
		logger.info("Inside searchBook method in UserController");
		try {
		return userService.searchBook(title,author,category,publisher,price);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MessageResponse("Something went wrong..!!"));
			
		}
	}

}
