package com.digitalbook.user.app.dto;

import java.time.LocalDate;

public class BookDTO {

	private int id;
	private String title;
	private String author;
	private String publisher;
	private String category;
	private double price;
	private String content;
	private String createdBy;
	private LocalDate createdDate;
	
	public BookDTO() {
	}
	
	public BookDTO(int id, String title, String author, String publisher, String category, double price, String content,
			String createdBy, LocalDate createdDate) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
		this.content = content;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", category=" + category + ", price=" + price + ", content=" + content + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + "]";
	}
	
	}
