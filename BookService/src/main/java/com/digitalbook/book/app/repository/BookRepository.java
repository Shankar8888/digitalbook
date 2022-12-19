package com.digitalbook.book.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbook.book.app.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
	@Query("select b from Book b where b.isBlocked=false and (b.title=?1 or b.author=?2 or b.category=?3 or b.publisher=?4 or b.price=?5)")
	public List<Book> findBookByFilters(String title, String author, String category, String publisher, Double price);

	@Modifying
	@Query("update Book b set b.isBlocked=?2 where b.id=?1")
	public void updateForBookBlocked(int bookId, boolean isblocked);

	@Query("select count(b.id) from Book b where b.isBlocked=false and b.title=?1 and b.author=?2")
	public int findBookByTitleAndAuthor(String title, String author);

	@Query("select b from Book b where b.isBlocked=false and b.id in(?1)")
	public List<Book> findAllBooksByIds(List<Integer> getAllBookIds);

	@Query("select b from Book b where b.author =?1")
	public List<Book> getBooksByAuthor(String authorName);


}
