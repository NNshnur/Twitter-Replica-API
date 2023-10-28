package com.cooksys.socialmedia.controllers;


import com.cooksys.socialmedia.dto.*;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.services.UserService;
import org.springframework.web.bind.annotation.*;
import com.cooksys.socialmedia.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/@{username}")
    public UserResponseDto getByUsername(@PathVariable String username){
        return userService.getByUsername(username);

}

    @PatchMapping("/@{username}")
    public UserResponseDto updateUsername(@RequestBody UserRequestDto userRequestDto, @PathVariable String username) {
        return userService.updateUserProfile(userRequestDto, username);
    }


    @DeleteMapping("/@{username}") public UserResponseDto deleteUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        return userService.deleteUser(credentialsDto);
    }

    @GetMapping("/@{username}/tweets") public List<TweetResponseDto> getTweetsFromUser(@PathVariable String username) {
        return userService.getAllTweetsByUser(username);
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username) {
		return userService.getUserFollowers(username);
	}
	
	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username) {
		return userService.getUserFollowing(username);
	}
	
	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username) {
		return userService.getUserMentions(username);
	}

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/@{username}/follow")
    public UserResponseDto followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.followUser(credentialsDto, username);
    }

    @PostMapping("/@{username}/unfollow")
    public UserResponseDto unFollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.unFollowUser(credentialsDto, username);
    }

    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable String username) {
        return userService.getUserFeed(username);
    }

}
