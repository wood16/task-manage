package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.mapper.UserMapper;
import com.example.taskmanage.mapper.UserStructMapper;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.utils.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private UserStructMapper userStructMapper;

    @Override
    public BaseResponseDto registerAccount(UserDto userDto) {
        BaseResponseDto responseDto = new BaseResponseDto();

        validateAccount(userDto);

        UserEntity userEntity = insertUser(userDto);

        try {

            userRepository.save(userEntity);
            responseDto.setCode(HttpStatus.OK.value());
            responseDto.setMessage("Create account success");
        } catch (Exception e) {

            responseDto.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
            responseDto.setMessage("Service unavailable");
        }

        return responseDto;
    }

    @Override
    public List<UserDto> getAllUser(String search) {

        if (Objects.nonNull(search)) {
            return userMapper.mapFromEntries(userRepository.findByUsernameContaining(search));
        } else {
            return userMapper.mapFromEntries(userRepository.findAll());
        }

    }

    @Override
    public List<UserDto> getAllUserRole(String search, String role) {

        return userMapper.mapFromEntries(userRepository.findAllUserWithoutRole(
                Objects.requireNonNullElse(role, "ADMIN"),
                Objects.requireNonNullElse(search, "")
        ));
    }


    @Override
    public UserDto getUserById(Long userId) {

        return userRepository.findById(userId)
                .map(item -> {
                    UserDto result = userStructMapper.toUserDto(item);

                    result.setRoles(userMapper.getRoles(item.getRoles()));

                    return result;
                })
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User not found"));
    }

    @Override
    public boolean checkUserRole(Long userId, String role) {

        return userRepository.findRoleByUser(userId).stream().anyMatch(item -> item.equalsIgnoreCase(role));
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
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Data must not empty");
        }

//        validate duplicate name
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername());
        if (Objects.nonNull(userEntity)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Username had existed");
        }

//        validate role
        List<String> roles = roleRepository.findAll().stream().map(RoleEntity::getName).toList();
        if (!roles.containsAll(Arrays.stream(userDto.getRoles()).toList())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Invalid role");
        }
    }
}
