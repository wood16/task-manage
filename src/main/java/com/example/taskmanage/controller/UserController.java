package com.example.taskmanage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/index")
    public ResponseEntity<String> index(Authentication authentication) {
        return ResponseEntity.ok("Wellcome to user Page : " + authentication.getName());
    }
}
