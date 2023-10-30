package com.cooksys.socialmedia.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
  
    private final HashtagMapper hashtagMapper;
    
    private final TweetRepository tweetRepository;
    
    private final TweetMapper tweetMapper;

    public List<HashtagResponseDto> getAllHashtags() {
        return hashtagMapper.hashtagEntitiesToResponseDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTweetsByHashtag(String label) {
        
        Hashtag hashtag = hashtagRepository.findByLabel(label);
        
        if (hashtag == null) {
        	throw new NotFoundException("Hashtag doesn't exist");
        }
        
        List<Tweet> associatedTweets = tweetRepository.findByHashtags_Label(hashtag.getLabel());

        return tweetMapper.entitiesToResponseDtos(associatedTweets);
    }

}

