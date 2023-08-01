package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.RetweetsDto;
import com.springboot.blog.repository.RetweetsRepository;
import com.springboot.blog.service.RetweetsService;
import com.springboot.blog.service.TweetsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class RetweetsController {
	
	RetweetsRepository retweetsRepository;
	RetweetsService  retweetsService;
	
	
	public RetweetsController(RetweetsRepository retweetsRepository, RetweetsService retweetsService) {
		super();
		this.retweetsRepository = retweetsRepository;
		this.retweetsService = retweetsService;
	}


	@PostMapping("retweet/{user_id}/{tweet_id}")
	public  ResponseEntity<RetweetsDto> createRetweet(@Valid @PathVariable(value="user_id") long user_id,
			@PathVariable(value="tweet_id") long tweet_id,
			@RequestBody RetweetsDto retweetsDto){
		
		 return new ResponseEntity<>(retweetsService.createRetweet(user_id, tweet_id,retweetsDto), HttpStatus.CREATED);
	}
	
	@GetMapping("retweetCount/{tweet_id}")
	public ResponseEntity<Long> getRetweetsCount(@Valid @PathVariable(value="tweet_id") long tweet_id,
			@RequestBody RetweetsDto retweetsDto
			){
		
		Long noOfRetweets = retweetsService.getRetweetsCount(tweet_id,retweetsDto);
		 return new ResponseEntity<Long>(noOfRetweets, HttpStatus.OK);
		
	}
}
