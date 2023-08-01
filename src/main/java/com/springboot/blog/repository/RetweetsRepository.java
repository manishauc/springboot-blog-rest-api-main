package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Retweets;
import com.springboot.blog.entity.Tweets;

@Repository
public interface RetweetsRepository extends JpaRepository<Retweets, Long> {
	
	List<Retweets> findByUserId(long user_id);
	
	@Query(value = "select T.id from Retweets R JOIN User U on R.user=U.id JOIN Tweets T on R.tweets=T.id WHERE U.id like ?1")
	List<Long> findRetweetsByUserId(long user_id);

  	
	
}
