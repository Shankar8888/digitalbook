package com.digitalbook.book.app.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BookEmbeddable implements Serializable {

	private static final long serialVersionUID = 1L;

		private String title; 
		private String author;

	    // default constructor

	    public BookEmbeddable(String title, String author) {
	        this.title = title;
	        this.author = author;
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

}
