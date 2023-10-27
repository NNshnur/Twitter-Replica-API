package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.*;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    public TweetResponseDto findById(Long Id);


    public List<UserResponseDto> getAllUserLikesOfTweet(Long id);

    public List<HashtagResponseDto> getAllTagsFromTweet(Long id);

    public List<TweetResponseDto> getAllRepliesFromTweet(Long id);

    public ContextDto getContextFromTweet(Long id);

    public TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto);

}
