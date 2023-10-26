package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
}
