package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.NotificationDto;
import com.example.taskmanage.entity.NotificationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationMapper {

    @Autowired
    private ModelMapper modelMapper;

    public NotificationDto mapFromEntity(NotificationEntity from){

        return modelMapper.map(from, NotificationDto.class);
    }

    public List<NotificationDto> mapFromEntities(List<NotificationEntity> from){

        return from.stream().map(this::mapFromEntity).toList();
    }
}
