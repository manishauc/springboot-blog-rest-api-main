package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.LikesDto;


import jakarta.validation.Valid;

public interface LikesService {

	LikesDto likeTweet(long tweetId, long userId, @Valid LikesDto likesDto);
	
//	List<LikesDto> getLikeByTweetId(long tweetId);

	  Long getLikesCount(long tweetId);
}
