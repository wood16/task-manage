package com.example.taskmanage.validator;

import com.example.taskmanage.dto.UserDto;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void validatorRegister(UserDto userDto){

        if(Objects.nonNull(userRepository.findByUsername(userDto.getUsername()))){

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Username already used!");
        }
    }
}
