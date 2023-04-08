package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskMapper {

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

        return to;
    }

    public List<TaskDto> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).collect(Collectors.toList());
    }
}
