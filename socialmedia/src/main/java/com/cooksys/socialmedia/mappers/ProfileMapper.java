package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dto.ProfileDto;
import com.cooksys.socialmedia.entities.Profile;

@Mapper(componentModel="spring")
public interface ProfileMapper {
	Profile dtoToEntity(ProfileDto profileDto);
}
