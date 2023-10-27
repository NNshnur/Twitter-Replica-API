package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	List<UserResponseDto> getUserFollowers(String username);
	
	List<UserResponseDto> getUserFollowing(String username);
	
	List<TweetResponseDto> getUserMentions(String username);
}
