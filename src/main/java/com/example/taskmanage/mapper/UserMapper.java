package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDto mapFromEntry(UserEntity from){
        UserDto to = new UserDto();

        to.setId(from.getId());
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());

        return to;
    }

    public List<UserDto> mapFromEntries(List<UserEntity> from){

        return from.stream().map(this::mapFromEntry).collect(Collectors.toList());
    }
}
