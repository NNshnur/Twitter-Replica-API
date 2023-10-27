package com.cooksys.socialmedia.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.User;



@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {



    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToResponseDto(User user);
    User dtoToEntity(UserRequestDto dto);
    List<UserResponseDto> entitiesToResponseDtos(List<User> users);

}