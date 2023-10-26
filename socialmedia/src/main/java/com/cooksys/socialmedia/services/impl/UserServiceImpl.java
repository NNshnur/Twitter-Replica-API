package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;

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

    @Override
    public List<TweetResponseDto> getUserFeed(String username) {
        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user == null) {
            throw new NotFoundException("User not found with username " + username);
        }
        List<Tweet> userTweets = tweetRepository.findByAuthorAndDeletedFalse(user);

        List<Tweet> followingTweets = new ArrayList<>();
        for (User followingUser : user.getFollowing()) {
            List<Tweet> tweetsByFollowingUser = tweetRepository.findByAuthorAndDeletedIsFalseAndContentIsNotNull(followingUser);
            followingTweets.addAll(tweetsByFollowingUser);
        }

        List<Tweet> allTweets = new ArrayList<>();
        allTweets.addAll(userTweets);
        allTweets.addAll(followingTweets);

        allTweets.sort((tweet1, tweet2) -> tweet2.getPosted().compareTo(tweet1.getPosted()));

        List<TweetResponseDto> tweetDtos = tweetMapper.entitiesToResponseDtos(allTweets);

        return tweetDtos;
    }
}

