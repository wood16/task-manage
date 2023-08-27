package com.example.taskmanage.controller;

import com.example.taskmanage.dto.RefreshTokenRequest;
import com.example.taskmanage.entity.RefreshToken;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/refreshToken")
public class RefreshTokenController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping()
    private ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserEntity)
                .map(userEntity -> {
                    String accessToken = jwtService.generateTokenForRefreshToken(userEntity.getId());

                    Map<String, String> responseObject = new HashMap<>();

                    responseObject.put("token", accessToken);
                    responseObject.put("refresh_token", refreshTokenRequest.getToken());
                    responseObject.put("message", "Refresh success!");

                    return new ResponseEntity(responseObject, HttpStatus.OK);
                })
                .orElseThrow(() ->
                        new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Token not found"));
    }
}
