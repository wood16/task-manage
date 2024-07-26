package com.example.taskmanage.mapper;

import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperUtil {

    @Autowired
    private UserRepository userRepository;

    public String getUserName(long userId) {

        return userRepository.findById(userId).map(UserEntity::getUsername).orElse("");
    }
}
