package com.digitalbook.book.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	
	 public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
	    }

}
