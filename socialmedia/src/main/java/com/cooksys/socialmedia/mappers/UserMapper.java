package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})

public interface UserMapper {
    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User user);
}