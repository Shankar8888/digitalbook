package com.digitalbook.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbook.book.app.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
	@Query("select b from Book b where b.isBlocked=?1 and b.title=?2 or b.author=?3 or b.category=?4 or b.publisher=?5 or b.price=?6")
	public List<Book> findBookByFilters(boolean blockStatus, String title, String author, String category, String publisher, double price);

	@Modifying
	@Query("update Book b set b.isBlocked=?2 where b.id=?1")
	public void updateForBookBlocked(int bookId, boolean isblocked);

	@Query("select count(b.id) from Book b where b.isBlocked=false and b.title=?1 and b.author=?2")
	public int findBookByTitleAndAuthor(String title, String author);

	@Query("select b from Book b where b.isBlocked=false and b.id in(?1)")
	public List<Book> findAllBooksByIds(List<Integer> getAllBookIds);

}
