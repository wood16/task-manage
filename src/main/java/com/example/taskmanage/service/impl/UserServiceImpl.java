package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.mapper.UserMapper;
import com.example.taskmanage.mapper.UserStructMapper;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.UserService;
import com.example.taskmanage.utils.BaseResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;
    UserMapper userMapper;
    UserStructMapper userStructMapper;

    @Override
    public BaseResponseDto registerAccount(UserRequest userRequest) {
        BaseResponseDto responseDto = new BaseResponseDto();

//        validateAccount(userDto);

        UserEntity userEntity = insertUser(userRequest);

//        try {

            userRepository.save(userEntity);
            responseDto.setCode(HttpStatus.OK.value());
            responseDto.setMessage("Create account success");
//        } catch (Exception e) {
//
//            responseDto.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
//            responseDto.setMessage("Service unavailable");
//        }

        return responseDto;
    }

    @Override
    public List<UserRequest> getAllUser(String search) {

        if (Objects.nonNull(search)) {
            return userMapper.mapFromEntries(userRepository.findByUsernameContaining(search));
        } else {
            return userMapper.mapFromEntries(userRepository.findAll());
        }

    }

    @Override
    public List<UserRequest> getAllUserRole(String search, String role) {

        return userMapper.mapFromEntries(userRepository.findAllUserWithoutRole(
                Objects.requireNonNullElse(role, "ADMIN"),
                Objects.requireNonNullElse(search, "")
        ));
    }


    @Override
    public UserRequest getUserById(Long userId) {

        return userRepository.findById(userId)
                .map(item -> {
                    UserRequest result = userStructMapper.toUserRequest(item);

                    result.setRoles(userMapper.getRoles(item.getRoles()));

                    return result;
                })
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User not found"));
    }

    @Override
    public boolean checkUserRole(Long userId, String role) {

        return userRepository.findRoleByUser(userId).stream().anyMatch(item -> item.equalsIgnoreCase(role));
    }

    private UserEntity insertUser(UserRequest userRequest) {

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        Arrays.stream(userRequest.getRoles()).forEach(item ->
                roles.add(roleRepository.findByName(item))
        );

        userEntity.setRoles(roles);

        return userEntity;
    }

    private void validateAccount(UserRequest userRequest) {

//        validate null data
        if (Objects.isNull(userRequest)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Data must not empty");
        }

//        validate duplicate name
        UserEntity userEntity = userRepository.findByUsername(userRequest.getUsername());
        if (Objects.nonNull(userEntity)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Username had existed");
        }

//        validate role
        List<String> roles = roleRepository.findAll().stream().map(RoleEntity::getName).toList();
        if (!roles.containsAll(Arrays.stream(userRequest.getRoles()).toList())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Invalid role");
        }
    }
}
