package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/@{username}") public User deleteUser(@RequestBody CredentialsDto credentialsDto) {
        return userService.deleteUser(credentialsDto);
    }

    // patch users @ username
    // use the if username exists to determine whether the user exists
}
