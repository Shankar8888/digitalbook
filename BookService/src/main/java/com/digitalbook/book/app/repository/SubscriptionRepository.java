package com.digitalbook.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbook.book.app.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	@Query("select count(s.id) from Subscription s where s.userId=?1 and s.book.id=?2 and s.isCancelled=false")
	public int findSubscriptionExists(int userId, int bookId);

	@Query("select s.book.id from Subscription s where s.userId=?1 and s.isCancelled=false")
	public List<Integer> findAllSubscribedBookIds(int userId);

	@Query("select s from Subscription s where s.userId=?1 and s.id=?2 and s.isCancelled=false")
	public Subscription findSubscriptionOfBook(int userId, int subscriptionId);

}
