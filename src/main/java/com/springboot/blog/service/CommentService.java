package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
	
	
      CommentDto createComment(long tweetId, long userId,CommentDto commentDto);

    List<CommentDto> getCommentsByTweetId(long tweetId);
    
    Long getCommentsByTweetIdCount(long tweetId);

   
}
