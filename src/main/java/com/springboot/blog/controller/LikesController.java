package com.springboot.blog.controller;


import com.springboot.blog.payload.LikesDto;
import com.springboot.blog.service.LikesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
public class LikesController {

     private LikesService likesService;
	
     public LikesController(LikesService likesService) {
 		super();
 		this.likesService = likesService;
 	}

     //like  a tweet
     @PostMapping("/likes/{tweetId}/{useId}")
     public ResponseEntity<LikesDto> likeTweet(@PathVariable(value = "tweetId") long tweetId,
    		 										 @PathVariable(value = "useId") long useId,
                                                     @Valid @RequestBody LikesDto likesDto){
         return new ResponseEntity<>(likesService.likeTweet(tweetId, useId,likesDto), HttpStatus.CREATED);
     }

     //Likes count for tweet
     @GetMapping("/tweets/{tweetId}/likesCount")
     public Long getLikesCount(@PathVariable(value = "tweetId") Long tweetId){
         return likesService.getLikesCount(tweetId);
     }


    
}
