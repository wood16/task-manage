package com.example.taskmanage.controller;

import com.example.taskmanage.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public BaseResponse<?> index(Principal principal) {

        return BaseResponse.builder()
                .message("Wellcome to admin Page : " + principal.getName())
                .build();
    }
}
