package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel="spring", uses = {UserMapper.class})
public interface TweetMapper {
        @Mapping(target="content", source="content")
        TweetResponseDto tweetEntityToResponseDto(Tweet entity);

        @Mapping(target="content", source = "content")
        List<TweetResponseDto> entitiesToResponseDtos(List<Tweet> allTweets);

        Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
