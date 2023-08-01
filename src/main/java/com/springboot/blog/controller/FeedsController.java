package com.springboot.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.TweetsDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.TweetsService;

@RestController
@RequestMapping("/api/v1/auth")
public class FeedsController {
	
	private UserRepository userRepository;
	private TweetsService tweetsService;

    public FeedsController(UserRepository userRepository,TweetsService tweetsService) {
        this.userRepository = userRepository;
        this.tweetsService =tweetsService;
    }
    
    //Start:: Tweets by user and those they followed
    
	@GetMapping("/userAndFollowedTweets/{id}")
	public List<TweetsDto> ResponseEntity(@PathVariable(value="id") long user_id){
		List<TweetsDto> TweetsList = tweetsService.getAllUserAndFollowedTweets(user_id);	
		 return TweetsList;
		 
	}
	 //End:: Tweets by user and those they followed
}
