package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;


@Mapper(componentModel="spring")
public interface HashtagMapper {
    TweetResponseDto entityToTweetResponseDto(Tweet tweet);
}


