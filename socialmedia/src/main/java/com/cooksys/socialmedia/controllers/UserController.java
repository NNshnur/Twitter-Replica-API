package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.services.UserService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{username}/follow")
    public UserResponseDto followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.followUser(credentialsDto, username);
    }

    @PostMapping("/{username}/unfollow")
    public UserResponseDto unFollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.unFollowUser(credentialsDto, username);
    }

}
