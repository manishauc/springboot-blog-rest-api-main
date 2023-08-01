package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDto {
	
	 private long id;
		
		private Boolean liked;
		
//		private long user_id;
//		
//		private long tweet_id;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Boolean getLiked() {
			return liked;
		}

		public void setLiked(Boolean liked) {
			this.liked = liked;
		}

//		public long getUser_id() {
//			return user_id;
//		}
//
//		public void setUser_id(long user_id) {
//			this.user_id = user_id;
//		}
//
//		public long getTweet_id() {
//			return tweet_id;
//		}
//
//		public void setTweet_id(long tweet_id) {
//			this.tweet_id = tweet_id;
//		}
//		
		
		
}
