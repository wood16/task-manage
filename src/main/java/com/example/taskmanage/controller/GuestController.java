package com.example.taskmanage.controller;

import com.example.taskmanage.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/index")
    public BaseResponse<?> index() {

        return BaseResponse.builder().message("Wellcome to my Page ").build();
    }
}
