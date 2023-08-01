package com.springboot.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.ProfileDto;
import com.springboot.blog.repository.FollowRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.ProfileService;


@Service
public class ProfileServiceImpl implements ProfileService{
	
	private UserRepository userRepository;

    private ModelMapper mapper;
    
    private FollowRepository followRepository;
	public ProfileServiceImpl(UserRepository userRepository, ModelMapper mapper,FollowRepository followRepository) {
		super();
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.followRepository =followRepository;
	}


	@Override
    public ProfileDto getProfileById(long id) {
        User profile = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));
        return mapToDTO(profile);
    }


	@Override
    public Long getUserFollowersById(long id) {
        User profile = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));
        		
        long noOfFollowers = 0;
         noOfFollowers = followRepository.findAllByFollowedTo(id);
        return noOfFollowers;
     //   return mapToDTO(profile);
    }


	@Override
	public ProfileDto updateProfile(ProfileDto profileDto, long id) {
		 // get post by id from the database
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));

//        Category category = categoryRepository.findById(postDto.getCategoryId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

 		user.setContact(profileDto.getContact());
 		user.setName(profileDto.getName());
 		user.setBio(profileDto.getBio());
 		
 		User updatedProfile = userRepository.save(user);
        return mapToDTO(updatedProfile);
		
	}
	

	   // convert Entity into DTO
		 private ProfileDto mapToDTO(User profile){
		     ProfileDto profileDto = mapper.map(profile, ProfileDto.class);
		     return profileDto;
		 }

    // convert DTO to entity
//    private User mapToEntity(ProfileDto profileDto){
//    	User profile = mapper.map(profileDto, User.class);
//        return profile;
//    }

}
