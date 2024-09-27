package com.example.taskmanage.validator;

import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidator {

    UserRepository userRepository;

    public void validatorRegister(UserRequest userRequest) {
//        TODO update unique field

        if (Objects.nonNull(userRepository.findByUsername(userRequest.getUsername()))) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Username already used!");
        }
    }
}
