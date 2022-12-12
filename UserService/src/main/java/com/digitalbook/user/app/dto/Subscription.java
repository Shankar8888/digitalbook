package com.digitalbook.user.app.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class Subscription {

	private Integer id;
	
	private int userId;

	private Book book;

	private LocalDateTime subscriptionDate;

	private boolean isCancelled;
	
	private LocalDateTime dateOfCancellation;
	



	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscription(Integer id, int userId, Book book, LocalDateTime subscriptionDate, boolean isCancelled,
			LocalDateTime dateOfCancellation) {
		super();
		this.id = id;
		this.userId = userId;
		this.book = book;
		this.subscriptionDate = subscriptionDate;
		this.isCancelled = isCancelled;
		this.dateOfCancellation = dateOfCancellation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDateTime getSubscriptionDate() {
		return subscriptionDate;
	}


	public void setSubscriptionDate(LocalDateTime subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}


	public boolean isCancelled() {
		return isCancelled;
	}


	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	public LocalDateTime getDateOfCancellation() {
		return dateOfCancellation;
	}


	public void setDateOfCancellation(LocalDateTime dateOfCancellation) {
		this.dateOfCancellation = dateOfCancellation;
	}


	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", book=" + book + ", subscriptionDate="
				+ subscriptionDate + ", isCancelled=" + isCancelled + ", dateOfCancellation=" + dateOfCancellation
				+ "]";
	}

}
