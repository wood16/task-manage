package com.example.taskmanage.jwt.impl;


import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.exception.ErrorCode;
import com.example.taskmanage.jwt.JwtConfig;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.service.security.UserDetailsCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {

    JwtConfig jwtConfig;
    UserDetailsService userDetailsService;
    UserService userService;

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());

        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom) {

        Instant now = Instant.now();

        List<String> roles = new ArrayList<>();

        userDetailsCustom.getAuthorities().forEach(role -> roles.add(role.getAuthority()));

        log.info("Roles: {}", roles);

        return Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .claim("userId", userDetailsCustom.getUserId())
                .claim("authorities",
                        userDetailsCustom.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .claim("roles", roles)
                .claim("isEnable", userDetailsCustom.isEnabled())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public boolean isValidToken(String token) {

        final String username = extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return Objects.nonNull(userDetails);
    }

    @Override
    public String generateTokenForRefreshToken(Long userId) {

        UserRequest userRequest = userService.getUserById(userId);

        return Jwts.builder()
                .setSubject(userRequest.getUsername())
                .claim("userId", userRequest.getId())
                .claim("authorities", Arrays.stream(userRequest.getRoles()).toList())
                .claim("roles", Arrays.stream(userRequest.getRoles()).toList())
                .claim("isEnable", Boolean.TRUE)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(1800)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String extractUsername(String token) {

        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {

        final Claims claims = extractAllClaims(token);

        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {

            throw new BaseException(ErrorCode.TOKEN_EXPIRATION);
        } catch (UnsupportedJwtException e) {

            throw new BaseException(ErrorCode.TOKEN_NOT_SUPPORT);
        } catch (MalformedJwtException e) {

            throw new BaseException(ErrorCode.INVALID_PART_TOKEN);
        } catch (SignatureException e) {

            throw new BaseException(ErrorCode.INVALID_FORMAT_TOKEN);
        }
//        catch (Exception e) {
//
//            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
//        }

        return claims;
    }
}
