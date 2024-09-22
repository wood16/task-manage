package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(UserDto userDto);

    @Mapping(target = "roles", ignore = true)
    UserDto toUserDto(UserEntity userEntity);
}
