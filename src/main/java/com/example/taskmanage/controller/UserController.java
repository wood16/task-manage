package com.example.taskmanage.controller;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserRequest>> getAllUser(
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(userService.getAllUser(search));
    }

    @GetMapping("/role/user")
    public ResponseEntity<List<UserRequest>> getAllUserRole(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role) {

        return ResponseEntity.ok(userService.getAllUserRole(search, role));
    }
}
