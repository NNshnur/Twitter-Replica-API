package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;

public interface UserService {
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	List<User> getUserFollowers(String username);
	
	List<User> getUserFollowing(String username);
	
	List<Tweet> getUserMentions(String username);
}
