package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ProfileDto;
import com.springboot.blog.payload.TweetsDto;
import com.springboot.blog.payload.ProfileDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.ProfileService;
import com.springboot.blog.service.TweetsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/profile")
@Tag(
        name = "CRUD REST APIs for Profile"
)
public class ProfileController {

	private ProfileService profileService;
	private UserRepository userRepository;
	private TweetsService tweetsService;

    public ProfileController(ProfileService profileService,UserRepository userRepository,TweetsService tweetsService) {
        this.profileService = profileService;
        this.userRepository = userRepository;
        this.tweetsService =tweetsService;
    }
    
    
 // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<Long> getUserFollowersById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(profileService.getUserFollowersById(id));
    }
    
    @GetMapping("/{id}/ListOfOwnTweets")
    public List<TweetsDto> getTweetsByUserId(@PathVariable(value = "id") Long user_id){
        return tweetsService.getTweetsByUserId(user_id);
    }
    
    @GetMapping("/{id}/OwnLikedRetweetedTweets")
    public List<TweetsDto> getAllOwnLikedAndRetweetedList(@PathVariable(value = "id") Long user_id){
        return tweetsService.getAllOwnLikedAndRetweetedList(user_id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> updatePost(@Valid @RequestBody ProfileDto profileDto, @PathVariable(name = "id") long id){

       ProfileDto profileResponse = profileService.updateProfile(profileDto, id);

       return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }
}
