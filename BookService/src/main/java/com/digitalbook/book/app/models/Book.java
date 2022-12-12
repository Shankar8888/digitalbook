package com.digitalbook.book.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "testdb2", name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Book Title is required")
	@Column(name = "book_title")
	private String title;
	
//	@NotBlank(message = "Book Author is required")
//	@Column(name = "author_id")
//	private Integer authorId;
	
	@NotBlank(message = "Book Author is required")
	@Column(name = "book_author")
	private String author;
	
	@NotBlank(message = "Book Publisher is required")
	@Column(name = "publisher")
	private String publisher;	

	@NotNull(message = "Book Price is required")
	@Column(name = "book_price")
	private double price;

	@Column(name = "book_category")
	private String category;
	
	@NotBlank
	@Column(name = "book_content")
	private String content;
	
	@Column(name = "published_date")
	private LocalDate publishedDate;
	
	@Column(name = "created_date")
	private LocalDateTime createdDateTime;

	@Column(name = "is_blocked")
	private Boolean isBlocked;
	
	@Lob
	@Column(name = "logo")
	private byte[] logo;

	
	public Book() {
	}
	


	public Book(int id, @NotBlank(message = "Book Title is required") String title,
			@NotBlank(message = "Book Author is required") String author,
			@NotBlank(message = "Book Publisher is required") String publisher,
			@NotNull(message = "Book Price is required") double price, String category, String content,
			LocalDate publishedDate, LocalDateTime createdDateTime, Boolean isBlocked, byte[] logo) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.category = category;
		this.content = content;
		this.publishedDate = publishedDate;
		this.createdDateTime = createdDateTime;
		this.isBlocked = isBlocked;
		this.logo = logo;
	}



	public static Book setExistingBookValues(Book book, Book bookExists) {
		
		if(book.getTitle()==null)
			book.setTitle(bookExists.getTitle());
		if(book.getAuthor()==null)
			book.setAuthor(bookExists.getAuthor());
		if(book.getPublisher()==null)
			book.setPublisher(bookExists.getPublisher());
		if(book.getPrice()==0)
			book.setPrice(bookExists.getPrice());
		if(book.getCategory()==null)
			book.setCategory(bookExists.getCategory());
		if(book.getContent()==null)
			book.setContent(bookExists.getContent());
		if(book.getPublishedDate()==null)
			book.setPublishedDate(bookExists.getPublishedDate());
		if(book.getCreatedDateTime()==null)
			book.setCreatedDateTime(bookExists.getCreatedDateTime());
		if(book.getIsBlocked()==null)
			book.setIsBlocked(bookExists.getIsBlocked());
		if(book.getLogo()==null)
			book.setLogo(bookExists.getLogo());
		return book;
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
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
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

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", logo=" + logo + ", publisher="
				+ publisher + ", price=" + price + ", category=" + category + ", content=" + content
				+ ", publishedDate=" + publishedDate + ", createdDateTime=" + createdDateTime + "]";
	}



}
