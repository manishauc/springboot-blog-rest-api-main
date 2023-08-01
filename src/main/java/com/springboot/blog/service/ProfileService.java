package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.ProfileDto;



public interface ProfileService {
	
  //  PostDto createPost(PostDto postDto);

   // PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	ProfileDto getProfileById(long id);

	ProfileDto updateProfile(ProfileDto profileDto, long id);
	
	 Long getUserFollowersById(long id);

    //void deletePostById(long id);

  //  List<PostDto> getPostsByCategory(Long categoryId);
    
}
