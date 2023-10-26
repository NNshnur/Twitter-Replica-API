package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto followUser(CredentialsDto credentialsDto, String username);

    UserResponseDto unFollowUser(CredentialsDto credentialsDto, String username);
}
