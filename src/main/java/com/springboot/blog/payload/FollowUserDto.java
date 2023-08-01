package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserDto {
	
	   private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
			
}
