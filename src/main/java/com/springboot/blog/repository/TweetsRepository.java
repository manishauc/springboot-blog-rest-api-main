package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Tweets;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets, Long> {
	
	List<Tweets> findByUserId(long user_id);
	
	
	Long findRetweetsById(long tweet_id);
}
