package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;

import java.util.List;

public interface HashtagService {
    List<TweetResponseDto> getTweetsByHashtag(String label);
}
