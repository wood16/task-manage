package com.example.taskmanage.service.impl;

import com.example.taskmanage.entity.RefreshToken;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.RefreshTokenRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String createRefreshToken(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        // TODO need to check refresh already have

        RefreshToken refreshToken = RefreshToken.builder()
                .userEntity(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(2))
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken){

        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);

            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Refresh token was expiration");
        }

        return refreshToken;
    }
}
