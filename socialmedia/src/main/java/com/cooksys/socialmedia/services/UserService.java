package com.cooksys.socialmedia.services;


import java.util.List;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.dto.CredentialsDto;

public interface UserService {
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	List<UserResponseDto> getUserFollowers(String username);
	
	List<UserResponseDto> getUserFollowing(String username);
	
	List<TweetResponseDto> getUserMentions(String username);

  List<UserResponseDto> getAllUsers();

   UserResponseDto followUser(CredentialsDto credentialsDto, String username);

   UserResponseDto unFollowUser(CredentialsDto credentialsDto, String username);

   List<TweetResponseDto> getUserFeed(String username);

}
