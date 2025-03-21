package com.example.taskmanage.jwt;

import com.example.taskmanage.service.security.UserDetailsCustom;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {

    Claims extractClaims(String token);

    Key getKey();

    String generateToken(UserDetailsCustom userDetailsCustom);

    boolean isValidToken(String token);

    String generateTokenForRefreshToken(Long userId);
}
