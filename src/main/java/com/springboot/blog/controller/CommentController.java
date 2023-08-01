package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

   @PostMapping("/tweets/{tweetId}/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "tweetId") long tweetId,
    												@PathVariable(value = "userId") long userId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(tweetId,userId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/tweets/{tweetId}/comments")
    public List<CommentDto> getCommentsByTweetId(@PathVariable(value = "tweetId") Long tweetId){
        return commentService.getCommentsByTweetId(tweetId);
    }
    	
    @GetMapping("/tweets/{tweetId}/commentsCount")
    public Long getCommentsByTweetCount(@PathVariable(value = "tweetId") Long tweetId){
        return commentService.getCommentsByTweetIdCount(tweetId);
    }

  

    
}
