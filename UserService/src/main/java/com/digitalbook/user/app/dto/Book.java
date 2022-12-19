package com.digitalbook.user.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Book {

	private int id;
	
	@NotBlank(message = "Book Title is required")
	private String title;
	
	@NotBlank(message = "Book Author is required")
	private String author;
	
	@NotBlank(message = "Publisher is required")
	private String publisher;
	private String category;
	
	@NotNull(message = "Book Price is required")
	private double price;
	
	@NotBlank(message = "Book Content is required")
	private String content;
	private LocalDate publishedDate;
	private LocalDateTime createdDateTime;
	private Boolean isBlocked;
	private byte[] logo;
	
	public Book() {
	}
	
	
	
	public Book(int id, String title, String author, String publisher, String category, double price, String content,
			LocalDate publishedDate, LocalDateTime createdDateTime, Boolean isBlocked, byte[] logo) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
		this.content = content;
		this.publishedDate = publishedDate;
		this.createdDateTime = createdDateTime;
		this.isBlocked = isBlocked;
		this.logo = logo;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDate getPublishedDate() {
		return publishedDate;
	}



	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}



	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}



	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}



	public Boolean getIsBlocked() {
		return isBlocked;
	}



	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}



	public byte[] getLogo() {
		return logo;
	}



	public void setLogo(byte[] logo) {
		this.logo = logo;
	}



	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", category=" + category + ", price=" + price + ", content=" + content 
				+ ", publishedDate=" + publishedDate + ", createdDateTime=" + createdDateTime + ", isBlocked="
				+ isBlocked + ", logo=" + Arrays.toString(logo) + "]";
	}



	
	}
