package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

@Mapper(componentModel="spring", uses = {UserMapper.class})
public interface TweetMapper {
	List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);
}
