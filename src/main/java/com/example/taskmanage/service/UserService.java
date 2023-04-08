package com.example.taskmanage.service;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.utils.BaseResponseDto;

public interface UserService {
    BaseResponseDto registerAccount(UserDto userDto);
}
