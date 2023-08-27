package com.example.taskmanage.controller;

import com.example.taskmanage.dto.RefreshTokenRequest;
import com.example.taskmanage.entity.RefreshToken;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refreshToken")
public class RefreshTokenController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

//    @PostMapping()
//    private ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
//
//        refreshTokenService.findByToken(refreshTokenRequest.getToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUserEntity)
//                .map(userEntity -> {
//                    String accessToken = jwtService.generateToken()
//                })
//
//    }
}
