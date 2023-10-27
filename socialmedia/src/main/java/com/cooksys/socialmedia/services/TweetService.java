package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getRepostsOfTweet(Long repostOf);

    List<UserResponseDto> getUsersMentioned(Long id);

    TweetResponseDto createTweetReply(Long id, TweetRequestDto tweetRequestDto);
}
