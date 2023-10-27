package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.Token;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

//    public TweetResponseDto findTweetById(Token.ID tweetId) {
//        return tweetMapper.tweetEntityToResponseDto(tweetRepository.findById(tweetId));
//    }


}
