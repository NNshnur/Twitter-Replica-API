package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

import java.util.Optional;

public interface TweetService {

    public TweetResponseDto findById(Long Id);

}
