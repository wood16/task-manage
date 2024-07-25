package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.ProgressHistoryDto;
import com.example.taskmanage.entity.ProgressHistoryEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressHistoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public ProgressHistoryDto mapFromEntity(ProgressHistoryEntity from){

        ProgressHistoryDto to = modelMapper.map(from, ProgressHistoryDto.class);

        to.setCreateName(getUserName(from.getCreatorId()));

        return to;
    }

    public List<ProgressHistoryDto> mapFromEntities(List<ProgressHistoryEntity> from){

        return from.stream().map(this::mapFromEntity).toList();
    }

    private String getUserName(long userId) {

        return userRepository.findById(userId).map(UserEntity::getUsername).orElse("");
    }
}
