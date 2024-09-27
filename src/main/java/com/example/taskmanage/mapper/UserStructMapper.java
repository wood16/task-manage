package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(UserRequest userRequest);

    @Mapping(target = "roles", ignore = true)
    UserRequest toUserRequest(UserEntity userEntity);
}
