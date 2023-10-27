package com.cooksys.socialmedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getProfile() == null || userRequestDto.getCredentials() == null) {
			throw new BadRequestException("User must provide email, username, and password!");
		}
		
		if (userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile().getEmail() == null
				|| userRequestDto.getCredentials().getUsername() == null) {
			throw new BadRequestException("User must provide email, username, and password!");
		}
	}
	
	// returns an error if username is taken
	// otherwise returns "available" or "deleted"
	private String checkUsernameStatus(String username) {
		Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
		if (optionalUser.isPresent() && optionalUser.get().isDeleted() == false) {
			throw new BadRequestException("Username is taken! Please choose another username.");
		}
		
		if (optionalUser.isPresent() && optionalUser.get().isDeleted()) {
			return "deleted";
		}
		// if it passes the 2 checks above, it's an available username
		return "availabe";
	}
	
	// returns user even if it has been deleted
	private User getUser(String username) {
		// able to grab users even if they have been deleted
		Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		return optionalUser.get();
	}
	
	private User activeUser(String username) {
		User user = getUser(username);
		if (user.isDeleted()) {
			throw new NotFoundException("User not found!");
		}
		return user;
	}
		
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		// userName, password, email, firstName, lastName, phone

		// validate given information and make sure username is unique
		validateUserRequest(userRequestDto);
		String username = userRequestDto.getCredentials().getUsername();
		String usernameStatus = checkUsernameStatus(username);
		String userRequestPassword = userRequestDto.getCredentials().getPassword();
		
		if (usernameStatus.equals("deleted")) {
			User deletedUser = getUser(userRequestDto.getCredentials().getUsername());
			// it says if credentials match, so i check to make sure password also matches
			if (userRequestPassword.equals(deletedUser.getCredentials().getPassword())) {
				deletedUser.setDeleted(false);
				return userMapper.entityToDto(userRepository.saveAndFlush(deletedUser));
			} else {
				throw new BadRequestException("Please enter the correct password to re-activate your account.");
			}
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(userMapper.dtoToEntity(userRequestDto)));
	}

	@Override
	public List<User> getUserFollowers(String username) {
		User user = activeUser(username);
		return user.getFollowers();
	}

	@Override
	public List<User> getUserFollowing(String username) {
		User user = activeUser(username);
		return user.getFollowing();
	}

	@Override
	public List<Tweet> getUserMentions(String username) {
		User user = activeUser(username);
		return user.getMentionedTweets();
	}
}
