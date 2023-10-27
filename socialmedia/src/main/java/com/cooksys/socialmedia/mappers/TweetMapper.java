package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", uses = {UserMapper.class})
public interface TweetMapper {

   TweetResponseDto entityToResponseDto(Tweet tweet);

}
