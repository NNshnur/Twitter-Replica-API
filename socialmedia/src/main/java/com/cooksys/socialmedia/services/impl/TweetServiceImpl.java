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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public TweetResponseDto findById(Long Id) {
        List<Tweet> allTweets = tweetRepository.findAll();
        for (Tweet t : allTweets) {
            if (t.getId() == Id) {
                TweetResponseDto foundTweet = new TweetResponseDto();
                // I did this manually because the mapper wasn't working
                foundTweet.setAuthor(userMapper.entityToResponseDto(t.getAuthor()));
                foundTweet.setId(Id);
                foundTweet.setPosted(t.getPosted());
                foundTweet.setContent(t.getContent());
                return foundTweet;
            }
        }
        TweetResponseDto nullTweet = new TweetResponseDto();
        return nullTweet;
    }

}
