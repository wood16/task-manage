package com.example.taskmanage.service.impl;

import com.example.taskmanage.entity.RefreshToken;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.exception.ErrorCode;
import com.example.taskmanage.repository.RefreshTokenRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;

    @Override
    public String createRefreshToken(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        // TODO need to check refresh already have

        RefreshToken refreshToken = RefreshToken.builder()
                .userEntity(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(900))
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);

            throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRATION);
        }

        return refreshToken;
    }
}
