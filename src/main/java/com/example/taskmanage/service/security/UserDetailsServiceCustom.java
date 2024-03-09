package com.example.taskmanage.service.security;

import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetailsCustom userDetailsCustom = getUserDetail(username);

        if (Objects.isNull(userDetailsCustom)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Invalid username or password!");
        }

        return userDetailsCustom;
    }

    private UserDetailsCustom getUserDetail(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);

        if (Objects.isNull(userEntity)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Invalid username or password!");
        }

        return new UserDetailsCustom(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
