package com.springboot.blog.service;


import com.springboot.blog.payload.FollowUserDto;

public interface FollowService {

	String followUser(long fromUserId, long toUserId, FollowUserDto followUserDto);


}
