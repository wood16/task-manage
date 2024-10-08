package com.example.taskmanage.controller;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    UserService userService;
    UserValidator userValidator;

    @PostMapping("/register")
    public BaseResponse<?> register(@RequestBody @Valid UserRequest userRequest) {

//        userValidator.validatorRegister(userDto);

        userService.registerAccount(userRequest);

        return BaseResponse.builder().message("Create account success").build();
    }


}
