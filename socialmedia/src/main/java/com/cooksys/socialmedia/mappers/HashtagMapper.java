package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;

import java.util.List;

@Mapper(componentModel="spring")
public interface HashtagMapper {
	Hashtag hashtagDtoToEntity(HashtagDto hashtagDto);

	HashtagResponseDto hashtagEntityToResponseDto(Hashtag entity);

	List<HashtagResponseDto> hashtagEntitiesToResponseDtos(List<Hashtag> entities);

}
