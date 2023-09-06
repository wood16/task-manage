package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity mapEntityFromModel(TaskDto from, TaskEntity to) {

        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setProgressType(from.getProgressType());
        to.setParentTask(taskRepository.findById(from.getParentId()).orElse(null));

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
        to.setTasks(mapModelsFromEntities(taskRepository.findByParentTask_Id(from.getId())));
//        to.setParentTask(
//                taskRepository.findById(from.getParentId()).map(this::mapModelFromEntity).orElse(new TaskDto()));

        return to;
    }

    public List<TaskDto> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).toList();
    }

    private String getUserName(long userId) {

        return userRepository.findById(userId).map(UserEntity::getUsername).orElse("");
    }
}
