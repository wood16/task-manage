package com.example.taskmanage.controller;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.utils.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerAccount(userDto));
    }
}
