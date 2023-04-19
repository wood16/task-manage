package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskMapper {

    @Autowired
    private UserRepository userRepository;

    public TaskEntity mapEntityFromModel(TaskDto from) {

        TaskEntity to = new TaskEntity();

        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setProgressType(from.getProgressType());

        return to;
    }

    public TaskDto mapModelFromEntity(TaskEntity from) {

        TaskDto to = new TaskDto();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setProgressType(from.getProgressType());
        to.setStatus(from.getStatus());
        to.setProgress(from.getProgress());
        to.setCreateDate(from.getCreateDate());
        to.setCreatorId(from.getCreatorId());
        to.setCreatorName(getUserName(from.getCreatorId()));
        to.setModifiedDate(from.getModifiedDate());
        to.setModifiedId(from.getModifiedId());
        to.setModifiedName(getUserName(from.getModifiedId()));

        return to;
    }

    public List<TaskDto> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).collect(Collectors.toList());
    }

    private String getUserName(long userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent())
            return userEntity.get().getUsername();

        return "";
    }
}
