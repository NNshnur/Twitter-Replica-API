package com.cooksys.socialmedia.services;


import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import java.util.List;

public interface UserService {
    public User updateUserProfile(UserRequestDto userRequestDto, String name);

    public User deleteUser(CredentialsDto credentialsDto);

    public List<TweetResponseDto> getAllTweetsByUser(String username);
	
	  UserResponseDto createUser(UserRequestDto userRequestDto);
	
	  List<UserResponseDto> getUserFollowers(String username);
	
	  List<UserResponseDto> getUserFollowing(String username);
	
  	List<TweetResponseDto> getUserMentions(String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto followUser(CredentialsDto credentialsDto, String username);

    UserResponseDto unFollowUser(CredentialsDto credentialsDto, String username);

     List<TweetResponseDto> getUserFeed(String username);

}
