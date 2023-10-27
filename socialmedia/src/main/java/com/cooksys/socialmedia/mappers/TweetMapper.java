package com.cooksys.socialmedia.mappers;

import java.util.List;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

import java.util.List;

@Mapper(componentModel="spring", uses = {UserMapper.class})
public interface TweetMapper {

   TweetResponseDto entityToResponseDto(Tweet tweet);

	  List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    @Mapping(target="content", source = "content")
    List<TweetResponseDto> entitiesToResponseDtos(List<Tweet> allTweets);

}
