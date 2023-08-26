package com.example.taskmanage.service;

import com.example.taskmanage.entity.RefreshToken;
import org.springframework.stereotype.Service;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);
}
