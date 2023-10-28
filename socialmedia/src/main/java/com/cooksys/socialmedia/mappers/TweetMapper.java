package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

@Mapper(componentModel="spring", uses = {UserMapper.class})
public interface TweetMapper {

        TweetResponseDto tweetEntityToResponseDto(Tweet entity);

        @Mapping(target="content", source = "content")
        List<TweetResponseDto> entitiesToResponseDtos(List<Tweet> allTweets);

        Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
