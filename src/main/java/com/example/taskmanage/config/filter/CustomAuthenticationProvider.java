package com.example.taskmanage.config.filter;

import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserEntity userEntity;
        try {
            userEntity = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "User's not found");
        }

        final List<GrantedAuthority> authorities = getAuthorities(userEntity.getRoles().stream().toList());

        final Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

        log.info("End actual authentication");

        return auth;
    }

    private List<GrantedAuthority> getAuthorities(List<RoleEntity> roles) {
        List<GrantedAuthority> result = new ArrayList<>();

        Set<String> permissions = new HashSet<>();

        if (Objects.nonNull(roles)) {
            roles.forEach(role -> {
                permissions.add(role.getName());
            });
        }

        permissions.forEach(p -> {
            result.add(new SimpleGrantedAuthority(p));
        });

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
