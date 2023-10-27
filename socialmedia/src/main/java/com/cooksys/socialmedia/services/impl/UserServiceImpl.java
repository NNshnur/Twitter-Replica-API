package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Profile;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final TweetMapper tweetMapper;

    private final TweetRepository tweetRepository;

    public boolean usernameExists(String username) {
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getCredentials().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public List<TweetResponseDto> getAllTweetsByUser(String username) {
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getCredentials().getUsername().equals(username)) {
                List<Tweet> usersTweets = u.getTweets();
                return tweetMapper.tweetEntitiesToResponseDtos(usersTweets);
            }
        }
       List<TweetResponseDto> tweets = new ArrayList<>();
        return tweets;
    }

    public User deleteUser(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        User userToDelete = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (userToDelete == null) {
            throw new NotFoundException("user doesn't exist");
        }
        List<User> allUsers = userRepository.findAll();
            for (User u : allUsers) {
                if (u.getCredentials().getUsername().equals(username)) {
                    u.setDeleted(true);
                    return u;
                }
            }
        return userToDelete;

    }
    // need to store deleted users in a different way, maybe create a new table
    // can set a boolean for deleted

    public User updateUserProfile(CredentialsDto credentialsDto, ProfileDto profileDto) {
        String username = credentialsDto.getUsername();
        if (usernameExists(username)) {
            List<User> allUsers = userRepository.findAll();
            User userToUpdate = new User();
            for (User u : allUsers) {
                if (u.getCredentials().getUsername().equals(username)) {
                    userToUpdate = u;
                }
            }
            Profile profileUpdate = new Profile();
            profileUpdate.setEmail(profileDto.getEmail());
            profileUpdate.setFirstName(profileDto.getFirstName());
            profileUpdate.setLastName(profileDto.getLastName());
            profileUpdate.setPhone(profileDto.getPhone());
            userToUpdate.setProfile(profileUpdate);
            Credentials credentialsUpdate = new Credentials();
            credentialsUpdate.setUsername(username);
            credentialsUpdate.setPassword(credentialsDto.getPassword());
            userToUpdate.setCredentials(credentialsUpdate);
            userRepository.saveAndFlush(userToUpdate);
            return userToUpdate;
        }
        else {
            return new User();
            // this should return an error exception
        }
    }


}
