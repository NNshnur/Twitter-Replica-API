package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})

public interface UserMapper {
    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToResponseDto(User user);


    List<UserResponseDto> entitiesToResponseDtos(List<User> users);
}