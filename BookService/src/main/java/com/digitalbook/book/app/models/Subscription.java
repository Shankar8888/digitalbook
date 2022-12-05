package com.digitalbook.book.app.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "testbd2", name = "subscription")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private int userId;

	@Column(name = "book_id")
	private int bookId;
	
	@Column(name = "create_date")
	private LocalDateTime createdDateTime;
	

	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Subscription(Integer id, int userId, int bookId, LocalDateTime createdDateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.createdDateTime = createdDateTime;
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

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", createdDateTime="
				+ createdDateTime + "]";
	}

}
