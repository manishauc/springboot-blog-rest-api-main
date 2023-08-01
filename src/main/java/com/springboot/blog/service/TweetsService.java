package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.TweetsDto;

public interface TweetsService {

	TweetsDto createTweet(long user_id, TweetsDto tweetsDto);
	
	List<TweetsDto> getTweetsByUserId(long user_id);
	
	List<TweetsDto> getAllOwnLikedAndRetweetedList(long user_id);
	
	List<TweetsDto> getAllUserAndFollowedTweets(long user_id);
	
	TweetsDto getTweetById(Long user_id, Long tweetId);

	TweetsDto updateTweet(Long user_id, long tweetId, TweetsDto tweetRequest);

    void deleteTweet(Long user_id, Long tweetId);

}
