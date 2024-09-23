package com.example.taskmanage.mapper;

import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapperUtil {

    UserRepository userRepository;

    public String getUserName(long userId) {

        return userRepository.findById(userId).map(UserEntity::getUsername).orElse("");
    }
}
