package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.entities.Credentials;

@Mapper(componentModel="spring")
public interface CredentialsMapper {
	Credentials dtoToEntity(CredentialsDto credentialsDto);
}
