package com.digitalbook.book.app.dto;

import java.time.LocalDate;
import java.util.Arrays;

public class BookResponse {

	private int id;
	private String title;
	private String author;
	private String publisher;
	private String category;
	private double price;
	private byte[] logo;
	private LocalDate publishedDate;

	public BookResponse() {
	}

	public BookResponse(int id, String title, String author, String publisher, String category, double price,
			byte[] logo, LocalDate publishedDate) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
		this.logo = logo;
		this.publishedDate = publishedDate;
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

	@Override
	public String toString() {
		return "BookResponse [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", category=" + category + ", price=" + price + ", logo=" + Arrays.toString(logo) + ", publishedDate="
				+ publishedDate + "]";
	}

}
