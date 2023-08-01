package com.springboot.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Retweets {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY
	)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tweets_id", nullable = false)
	private Tweets tweets;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tweets getTweets() {
		return tweets;
	}

	public void setTweets(Tweets tweets) {
		this.tweets = tweets;
	}
	
	
	
}
