package com.example.taskmanage.service;

import com.example.taskmanage.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken refreshToken);
}
