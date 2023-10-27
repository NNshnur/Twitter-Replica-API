package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;

import java.util.List;

public interface UserService {
    public User updateUserProfile(CredentialsDto credentialsDto, ProfileDto profileDto);

    public User deleteUser(CredentialsDto credentialsDto);

    public List<TweetResponseDto> getAllTweetsByUser(String username);
}
