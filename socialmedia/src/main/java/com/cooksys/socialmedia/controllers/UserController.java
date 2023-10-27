package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/users")
public class UserController {

    private UserService userService;

    private ValidateService validateService;


    @PatchMapping("/@{username}") public User updateUsername(@RequestBody CredentialsDto credentialsDto, @RequestBody ProfileDto profileDto) {
        return userService.updateUserProfile(credentialsDto, profileDto);
    }

    // patch users @ username
    // use the if username exists to determine whether the user exists
}
