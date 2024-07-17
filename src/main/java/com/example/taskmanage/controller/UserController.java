package com.example.taskmanage.controller;

import com.example.taskmanage.dto.UserContextDto;
import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public ResponseEntity<String> index() {

        UserContextDto userContextDto = (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("LAM" + userContextDto.getUserId());

        return ResponseEntity.ok("Wellcome to user Page : " + userContextDto.getUserName());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser(
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(userService.getAllUser(search));
    }

    private UserContextDto getUserContext() {

        return (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
