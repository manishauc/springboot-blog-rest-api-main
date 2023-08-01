package com.springboot.blog.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Tweets;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.TweetsDto;
import com.springboot.blog.repository.FollowRepository;
import com.springboot.blog.repository.LikesRepository;
import com.springboot.blog.repository.RetweetsRepository;
import com.springboot.blog.repository.TweetsRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.TweetsService;


@Service
public class TweetsServiceImpl implements TweetsService{
	
	    private TweetsRepository tweetsRepository;	  
	    private UserRepository userRepository; 
	    private ModelMapper modelMapper;
	    private LikesRepository likesRepository;    
	    private FollowRepository followRepository;
	    private RetweetsRepository retweetsRepository;

	  
	
	    public TweetsServiceImpl(TweetsRepository tweetsRepository, UserRepository userRepository,
				ModelMapper modelMapper ,LikesRepository likesRepository,FollowRepository followRepository,RetweetsRepository retweetsRepository) {
			super();
			this.tweetsRepository = tweetsRepository;
			this.userRepository = userRepository;
			this.modelMapper = modelMapper;
			this.likesRepository=likesRepository;
			this.followRepository =followRepository;
			this.retweetsRepository=retweetsRepository;
		}

		//START :: Create tweet
	    @Override
	    public TweetsDto createTweet(long user_id, TweetsDto tweetsDto) {
	
	        Tweets tweet = mapToEntity(tweetsDto);
	
	        // retrieve user entity by id
	        User userid = userRepository.findById(user_id).orElseThrow(
	                () -> new ResourceNotFoundException("User", "id", user_id));
	
	        // set user to tweet entity
	        tweet.setUser(userid);
	        //Check tweet content length for validation
	        if(tweet.getTweet_content().length()>280) {
				
				throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Length must be between 0 to 280 characters!.");
			}
	        // tweet entity to DB
	        Tweets newTweet =  tweetsRepository.save(tweet);
	
	        return mapToDTO(newTweet);
	    }
	    //END :: Create tweet
    
    
	    //START :: Get Tweet By ID
		@Override
		public List<TweetsDto> getTweetsByUserId(long user_id) {
			 List<Tweets> tweets = tweetsRepository.findByUserId(user_id);
	
		        // convert list of tweet entities to list of tweet dto's
		        return tweets.stream().map(tweet -> mapToDTO(tweet)).collect(Collectors.toList());
			
		}
		//END :: Get Tweet By ID
	
		//START :: View a list of their own tweets, as well as tweets they've liked and retweeted
		@Override
		public List<TweetsDto> getAllOwnLikedAndRetweetedList(long user_id) {
		
			//Tweets by user
			 List<Tweets> Usertweets = tweetsRepository.findByUserId(user_id);
			 
			//Tweets liked by user
			 List<Long> UserLikedtweetsId = likesRepository.findLikesById(user_id);		
			 List<Tweets> likedTweets =tweetsRepository.findAllById(UserLikedtweetsId); 
			 Usertweets.addAll(likedTweets);
			 
			//Retweeted tweets by user
			 List<Long> UserRetweetsId = retweetsRepository.findRetweetsByUserId(user_id);	
			  System.out.println(UserRetweetsId);
			 List<Tweets> RetweetedTweets =tweetsRepository.findAllById(UserRetweetsId); 
			 Usertweets.addAll(RetweetedTweets);
			 
			 return Usertweets.stream().map(tweet -> mapToDTO(tweet)).collect(Collectors.toList());
		
		}
		//END  :: View a list of their own tweets, as well as tweets they've liked and retweeted
		
		//START :: A list of tweets from the user and those they follow
		@Override
		public List<TweetsDto> getAllUserAndFollowedTweets(long user_id) {
			
			//Tweets by user
			 List<Tweets> Usertweets = tweetsRepository.findByUserId(user_id);
			 
			//Tweets  by followed user
			 List<Long> UserFollowedtweetsId = followRepository.findFollowedById(user_id);		
			 System.out.println(UserFollowedtweetsId);
			 
			 for (Long userId : UserFollowedtweetsId) {
				 System.out.println(userId);
				 List<Tweets> followedTweet = tweetsRepository.findByUserId(userId);
				
				 Usertweets.addAll(followedTweet);
			}
			 return Usertweets.stream()
					  .sorted(Comparator.comparing(Tweets::getId))
					  .map(tweet -> mapToDTO(tweet))
					  .collect(Collectors.toList());
			
		}
		//END :: A list of tweets from the user and those they follow

		@Override
		public TweetsDto getTweetById(Long user_id, Long tweetId) {
			   // retrieve user entity by id
	        User user = userRepository.findById(user_id).orElseThrow(
	                () -> new ResourceNotFoundException("User", "id", user_id));
	
	        // retrieve tweet by id
	        Tweets tweet = tweetsRepository.findById(tweetId).orElseThrow(() ->
	                new ResourceNotFoundException("Tweet", "id", tweetId));
	
	        if(!tweet.getUser().getId().equals(user.getId())){
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Tweet does not belong to user");
	        }
	
	        return mapToDTO(tweet);
		}


		@Override
		public TweetsDto updateTweet(Long user_id, long tweetId, TweetsDto tweetRequest) {
			// retrieve user entity by id
	        User user = userRepository.findById(user_id).orElseThrow(
	                () -> new ResourceNotFoundException("User", "id", user_id));
	
	        // retrieve tweet by id
	        Tweets tweet = tweetsRepository.findById(tweetId).orElseThrow(() ->
	                new ResourceNotFoundException("Tweet", "id", tweetId));
	
	        if(!tweet.getUser().getId().equals(user.getId())){
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Tweet does not belongs to user");
	        }
	
	        tweet.setTweet_content(tweetRequest.getTweet_content());
	        tweet.setImage(tweetRequest.getImage());
	       
	
	        Tweets updatedTweet = tweetsRepository.save(tweet);
	        return mapToDTO(updatedTweet);
		}


		@Override
		public void deleteTweet(Long user_id, Long tweetId) {
			 // retrieve user entity by id
	        User user = userRepository.findById(user_id).orElseThrow(
	                () -> new ResourceNotFoundException("User", "id", user_id));
	
	        // retrieve tweet by id
	        Tweets tweet = tweetsRepository.findById(tweetId).orElseThrow(() ->
	                new ResourceNotFoundException("Tweet", "id", tweetId));
	
	        if(!tweet.getUser().getId().equals(user.getId())){
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Tweet does not belongs to user");
	        }
	
	        tweetsRepository.delete(tweet);
			
		}
	
	    private TweetsDto mapToDTO(Tweets tweets){
	    	TweetsDto tweetsDto = modelMapper.map(tweets, TweetsDto.class);
	
	        return  tweetsDto;
	    }
	
	    private Tweets mapToEntity(TweetsDto tweetsDto){
	    	Tweets tweets = modelMapper.map(tweetsDto, Tweets.class);
	        return  tweets;
	    }


	


}
