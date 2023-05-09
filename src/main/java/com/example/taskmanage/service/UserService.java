package com.example.taskmanage.service;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.utils.BaseResponseDto;

import java.util.List;

public interface UserService {
    BaseResponseDto registerAccount(UserDto userDto);

    List<UserDto> getAllUser(String search);
}
