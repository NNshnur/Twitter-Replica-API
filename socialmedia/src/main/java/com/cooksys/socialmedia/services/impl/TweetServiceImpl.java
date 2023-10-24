package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;
}
