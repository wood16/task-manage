package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.ProgressHistoryDto;
import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.entity.UserEntity;
import com.example.taskmanage.repository.ProgressHistoryRepository;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProgressHistoryRepository progressHistoryRepository;

    @Autowired
    private ProgressHistoryMapper progressHistoryMapper;

    public TaskEntity mapEntityFromModel(TaskDto from, TaskEntity to) {

        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setProgressType(from.getProgressType());
        to.setAssigneeId(from.getAssigneeId());
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
//        to.setTasks(mapModelsFromEntities(taskRepository.findByParentTask_Id(from.getId())));
        Optional.ofNullable(from.getParentTask()).ifPresent(entity -> {
            to.setParentTask(modelMapper.map(entity, TaskDto.class));
            to.setParentId(entity.getId());
        });
        to.setAssigneeId(from.getAssigneeId());
        to.setAssigneeName(
                getUserName(Objects.requireNonNullElse(from.getAssigneeId(), 0L)));

        to.setProgressHistories(
                progressHistoryMapper.mapFromEntities(progressHistoryRepository.findByTaskId(from.getId()))
                        .stream().sorted(Comparator.comparing(ProgressHistoryDto::getCreateDate).reversed()).toList());

        return to;
    }

    public List<TaskDto> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).toList();
    }

    private String getUserName(long userId) {

        return userRepository.findById(userId).map(UserEntity::getUsername).orElse("");
    }
}
