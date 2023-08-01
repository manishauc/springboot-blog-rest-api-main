package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetsDto {
	
    private long id;
	
	private String tweet_content;
	
	private String image;
	
	private long user_id;
	
	private long retweets;
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTweet_content() {
		return tweet_content;
	}

	public void setTweet_content(String tweet_content) {
		this.tweet_content = tweet_content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getRetweets() {
		return retweets;
	}

	public void setRetweets(long retweets) {
		this.retweets = retweets;
	}
	
	
}
