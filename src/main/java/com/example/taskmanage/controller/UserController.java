package com.example.taskmanage.controller;

import com.example.taskmanage.dto.UserContextDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/index")
    public ResponseEntity<String> index() {

        UserContextDto userContextDto = (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("LAM" + userContextDto.getUserId());

        return ResponseEntity.ok("Wellcome to user Page : " + userContextDto.getUserName());
    }
}
