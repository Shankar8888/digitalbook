package com.digitalbook.user.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.user.app.dto.BookDTO;
import com.digitalbook.user.app.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<?> searchBook(String title, String author, String category, String publisher, double price) {
		// TODO Auto-generated method stub
		    String url="http://localhost:8081/book/searchBook";
		    BookDTO bookResult=  (BookDTO) restTemplate.getForObject(url, List.class);
		    return ResponseEntity.ok(bookResult);
	}

	
}
