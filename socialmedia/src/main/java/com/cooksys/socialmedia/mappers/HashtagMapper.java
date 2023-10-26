package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;

@Mapper(componentModel="spring")
public interface HashtagMapper {
	Hashtag hashtagDtoToEntity(HashtagDto hashtagDto);
}
