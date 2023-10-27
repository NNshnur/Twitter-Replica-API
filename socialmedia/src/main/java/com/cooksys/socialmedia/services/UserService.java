package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
//	List<User> getUserFollowers(String username);
}
