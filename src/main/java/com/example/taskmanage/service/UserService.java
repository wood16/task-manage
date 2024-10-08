package com.example.taskmanage.service;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.dto.response.BaseResponse;

import java.util.List;

public interface UserService {
    void registerAccount(UserRequest userRequest);

    List<UserRequest> getAllUser(String search);

    List<UserRequest> getAllUserRole(String search, String role);

    UserRequest getUserById(Long userId);

    boolean checkUserRole(Long userId, String role);
}
