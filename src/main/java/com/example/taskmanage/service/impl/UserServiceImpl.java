package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.mapper.UserMapper;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.utils.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponseDto registerAccount(UserDto userDto) {
        BaseResponseDto responseDto = new BaseResponseDto();

        validateAccount(userDto);

        UserEntity userEntity = insertUser(userDto);

        try {

            userRepository.save(userEntity);
            responseDto.setCode(String.valueOf(HttpStatus.OK.value()));
            responseDto.setMessage("Create account success");
        } catch (Exception e) {

            responseDto.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            responseDto.setMessage("Service unavailable");
        }

        return responseDto;
    }

    @Override
    public List<UserDto> getAllUser(String search) {

        if(Objects.nonNull(search)){
            return userMapper.mapFromEntries(userRepository.findByUsernameContaining(search));
        }
        else {
            return userMapper.mapFromEntries(userRepository.findAll());
        }

    }

    @Override
    public UserDto getUserById(Long userId) {

        return userRepository.findById(userId)
                .map(item -> userMapper.mapFromEntry(item))
                .orElseThrow(() -> new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "User not found"));
    }

    private UserEntity insertUser(UserDto userDto) {

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        Arrays.stream(userDto.getRoles()).forEach(item ->
                roles.add(roleRepository.findByName(item))
        );

        userEntity.setRoles(roles);

        return userEntity;
    }

    private void validateAccount(UserDto userDto) {

//        validate null data
        if (Objects.isNull(userDto)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data must not empty");
        }

//        validate duplicate name
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername());
        if (Objects.nonNull(userEntity)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Username had existed");
        }

//        validate role
        List<String> roles = roleRepository.findAll().stream().map(RoleEntity::getName).collect(Collectors.toList());
        if (!roles.containsAll(Arrays.stream(userDto.getRoles()).collect(Collectors.toList()))) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }
    }
}
