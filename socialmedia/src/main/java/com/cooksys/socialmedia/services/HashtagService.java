package com.cooksys.socialmedia.services;


import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import java.util.List;

public interface HashtagService {

    public List<HashtagResponseDto> getAllHashtags();
  
    public List<TweetResponseDto> getTweetsByHashtag(String label);

}
