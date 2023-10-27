package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    public TweetResponseDto findById(Long Id);


    public List<UserResponseDto> getAllUserLikesOfTweet(Long id);

    public List<HashtagResponseDto> getAllTagsFromTweet(Long id);

}
