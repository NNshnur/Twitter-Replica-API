package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.entities.User;

public interface UserService {
    public User updateUserProfile(CredentialsDto credentialsDto, ProfileDto profileDto);
}
