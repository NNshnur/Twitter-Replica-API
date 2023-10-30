package com.cooksys.socialmedia.mappers;


import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface HashtagMapper {
  
	TweetResponseDto entityToTweetResponseDto(Tweet tweet);
  
	Hashtag hashtagDtoToEntity(HashtagDto hashtagDto);

	HashtagResponseDto hashtagEntityToResponseDto(Hashtag entity);

	List<HashtagResponseDto> hashtagEntitiesToResponseDtos(List<Hashtag> entities);
}


