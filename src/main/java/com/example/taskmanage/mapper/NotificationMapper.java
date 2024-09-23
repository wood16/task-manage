package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.NotificationDto;
import com.example.taskmanage.entity.NotificationEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationMapper {

    ModelMapper modelMapper;

    public NotificationDto mapFromEntity(NotificationEntity from) {

        return modelMapper.map(from, NotificationDto.class);
    }

    public List<NotificationDto> mapFromEntities(List<NotificationEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }
}
