package com.springboot.blog.service.impl;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Retweets;
import com.springboot.blog.entity.Tweets;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.RetweetsDto;
import com.springboot.blog.repository.RetweetsRepository;
import com.springboot.blog.repository.TweetsRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.RetweetsService;

@Service
public class RetweetsServiceImpl implements RetweetsService{

	private ModelMapper modelMapper;
	private UserRepository userRepository; 
	private RetweetsRepository retweetsRepository;
	private TweetsRepository tweetsRepository;
	
	

	public RetweetsServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
			RetweetsRepository retweetsRepository, TweetsRepository tweetsRepository) {
		super();
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.retweetsRepository = retweetsRepository;
		this.tweetsRepository = tweetsRepository;
	}
	
	//Start :: Retweet the tweet
	@Override
	public RetweetsDto createRetweet(long user_id, long tweet_id, RetweetsDto retweetsDto) {
		
		Retweets retweets = mapToEntity(retweetsDto);
		
		 // retrieve user entity by id
        User userid = userRepository.findById(user_id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user_id));
        
     // retrieve tweet entity by id
        Tweets tweetId = tweetsRepository.findById(tweet_id).orElseThrow(
                () -> new ResourceNotFoundException("Tweet", "id", tweet_id));
        
        // set user to retweet entity
        retweets.setUser(userid);
        
        retweets.setTweets(tweetId);
        
        // retweet entity to DB
        Retweets newRetweet =  retweetsRepository.save(retweets);
        
        //Increate retweet count in tweets
        long retweet_count = tweetId.getRetweets();
        retweet_count+=1;
        tweetId.setRetweets(retweet_count);
         tweetsRepository.save(tweetId);
        retweetsDto = mapToDTO(newRetweet);
        retweetsDto.setUser_id(user_id);
        retweetsDto.setTweet_id(tweet_id);
        return retweetsDto;
		
	}
	//END :: Retweet the tweet
	
	//START :: No. of retweets count
	@Override
	public Long getRetweetsCount(long tweet_id, RetweetsDto retweetsDto) {
		  // retrieve tweet entity by id
        Tweets tweetId = tweetsRepository.findById(tweet_id).orElseThrow(
                () -> new ResourceNotFoundException("Tweet", "id", tweet_id));
		
		long noOfRetweets = tweetId.getRetweets();
		
		return noOfRetweets;
	}
	//END :: No. of retweets count
	
	
	private RetweetsDto mapToDTO(Retweets newRetweet) {
		RetweetsDto retweetsDto = modelMapper.map(newRetweet,RetweetsDto.class);
		return retweetsDto;
	}

	private Retweets mapToEntity(RetweetsDto retweetsDto) {
		
		Retweets retweets = modelMapper.map(retweetsDto,Retweets.class);
		return retweets;
	}

	

	

}
