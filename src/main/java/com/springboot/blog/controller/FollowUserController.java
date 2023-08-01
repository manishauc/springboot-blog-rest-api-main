package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.FollowUser;
import com.springboot.blog.payload.FollowUserDto;
import com.springboot.blog.payload.LikesDto;
import com.springboot.blog.service.FollowService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class FollowUserController {
	
	private FollowService followService;
	
	
	 public FollowUserController(FollowService followService) {
		super();
		this.followService = followService;
	}


	//like  a tweet
    @PostMapping("/follow/{fromUserId}/{toUserId}")
    public ResponseEntity<String> followUser(@PathVariable(value = "fromUserId") long fromUserId,
   		 										 @PathVariable(value = "toUserId") long toUserId,
                                                    @Valid @RequestBody FollowUserDto followUserDto){
        return new ResponseEntity<>(followService.followUser(fromUserId, toUserId,followUserDto), HttpStatus.CREATED);
    }
}
