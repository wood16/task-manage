package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProgressHistoryMapper progressHistoryMapper;

    @Autowired
    private MapperUtil mapperUtil;

    public TaskEntity mapEntityFromModel(TaskDto from, TaskEntity to) {

        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setAssigneeId(from.getAssigneeId());
        if (Objects.nonNull(from.getParentId())) {
            to.setParentTask(taskRepository.findById(from.getParentId()).orElse(null));
        }

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
        to.setStatus(from.getStatus());
        to.setProgress(from.getProgress());
        to.setCreateDate(from.getCreateDate());
        to.setCreatorId(from.getCreatorId());
        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));
        to.setModifiedDate(from.getModifiedDate());
        to.setModifiedId(from.getModifiedId());
        to.setModifiedName(mapperUtil.getUserName(from.getModifiedId()));
//        to.setTasks(mapModelsFromEntities(taskRepository.findByParentTask_Id(from.getId())));

        Optional.ofNullable(from.getParentTask()).ifPresent(entity -> {
            to.setParentTask(modelMapper.map(entity, TaskDto.class));
            to.setParentId(entity.getId());
        });

        to.setAssigneeId(from.getAssigneeId());
        to.setAssigneeName(mapperUtil.getUserName(
                Objects.requireNonNullElse(from.getAssigneeId(), 0L)));

        to.setProgressHistories(progressHistoryMapper.mapFromTaskId(from.getId()));

        return to;
    }

    public List<TaskDto> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).toList();
    }

    public TaskElasticModel mapForIndex(TaskEntity from) {

        TaskElasticModel to = modelMapper.map(from, TaskElasticModel.class);

        to.setPriorityNumber(getPriorityNumber(from.getPriority()));

        return to;
    }

    public List<TaskElasticModel> mapForIndexList(List<TaskEntity> from) {

        return from.stream().map(this::mapForIndex).toList();
    }

    public TaskDto mapFromElasticModel(TaskElasticModel from) {
        TaskDto to = new TaskDto();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setStatus(from.getStatus());
        to.setProgress(from.getProgress());
        to.setCreateDate(from.getCreateDate());
        to.setCreatorId(from.getCreatorId());
        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));
        to.setModifiedDate(from.getModifiedDate());
        to.setModifiedId(from.getModifiedId());
        to.setModifiedName(mapperUtil.getUserName(from.getModifiedId()));

        Optional.ofNullable(from.getParentTask()).ifPresent(entity -> {
            to.setParentTask(modelMapper.map(entity, TaskDto.class));
            to.setParentId(entity.getId());
        });

        to.setAssigneeId(from.getAssigneeId());
        to.setAssigneeName(mapperUtil.getUserName(
                Objects.requireNonNullElse(from.getAssigneeId(), 0L)));

        return to;
    }

    public List<TaskDto> mapFromElasticModels(List<TaskElasticModel> from) {

        return from.stream().map(this::mapFromElasticModel).toList();
    }

    private long getPriorityNumber(String priority) {

        return switch (priority) {
            case "high" -> 3;
            case "normal" -> 2;
            case "low" -> 1;
            default -> 0;
        };
    }
}
