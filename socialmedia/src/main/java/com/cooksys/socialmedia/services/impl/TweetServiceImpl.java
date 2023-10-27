package com.cooksys.socialmedia.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;
}
