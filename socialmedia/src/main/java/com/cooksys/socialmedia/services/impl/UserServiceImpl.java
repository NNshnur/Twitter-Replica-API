package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private User validateCredentials(CredentialsDto credentialsDto) {
        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        if (user != null && user.getCredentials().getPassword().equals(credentialsDto.getPassword())) {
        return user;
    } else {
            throw new BadRequestException("The password provided is incorrect");
        }

}
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository.findByDeletedFalse());
    }

    @Override
    public UserResponseDto followUser(CredentialsDto credentialsDto,String username) {
        User user = validateCredentials(credentialsDto);
        if(user == null) {
            throw new NotAuthorizedException("Invalid user");
        }

        User userToFollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if(userToFollow == null) {
            throw new NotFoundException("User to follow was not found");
        }
        if (user.getFollowing().contains(userToFollow)) {
            throw new BadRequestException("User is already following that user");
        }

        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        userRepository.saveAndFlush(user);

        return userMapper.entityToResponseDto(userToFollow);
    }

    @Override
    public UserResponseDto unFollowUser(CredentialsDto credentialsDto, String username) {
        User user = validateCredentials(credentialsDto);

        if (user == null) {
            throw new NotAuthorizedException("User is invalid");
        }

        User userToUnfollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(userToUnfollow == null) {
            throw new NotFoundException("User to unfollow was not found");
        }
        if(!user.getFollowing().contains(userToUnfollow)) {
            throw new BadRequestException("User is not following that user");
        }

        user.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(user);
        userRepository.saveAndFlush(user);

        return userMapper.entityToResponseDto(userToUnfollow);
        }
    }

