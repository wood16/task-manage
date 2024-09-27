package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.request.TaskRequest;
import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskMapper {

    ModelMapper modelMapper;
    TaskRepository taskRepository;
    ProgressHistoryMapper progressHistoryMapper;
    MapperUtil mapperUtil;
    TaskStructMapper taskStructMapper;

    public TaskEntity mapEntityFromModel(TaskRequest from, TaskEntity to) {

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

    public TaskResponse mapModelFromEntity(TaskEntity from) {

        TaskResponse to = new TaskResponse();

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
            to.setParentTask(modelMapper.map(entity, TaskResponse.class));
            to.setParentId(entity.getId());
        });

        to.setAssigneeId(from.getAssigneeId());
        to.setAssigneeName(mapperUtil.getUserName(
                Objects.requireNonNullElse(from.getAssigneeId(), 0L)));

        to.setProgressHistories(progressHistoryMapper.mapFromTaskId(from.getId()));

        return to;
    }

    public List<TaskResponse> mapModelsFromEntities(List<TaskEntity> from) {

        return from.stream().map(this::mapModelFromEntity).toList();
    }

    public TaskElasticModel mapForIndex(TaskEntity from) {

        TaskElasticModel to = taskStructMapper.mapForIndex(from);

        to.setPriorityNumber(getPriorityNumber(from.getPriority()));

        return to;
    }

    public List<TaskElasticModel> mapForIndexList(List<TaskEntity> from) {

        return from.stream().map(this::mapForIndex).toList();
    }

    public TaskResponse mapFromElasticModel(TaskElasticModel from) {
        TaskResponse to = new TaskResponse();

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
            to.setParentTask(modelMapper.map(entity, TaskResponse.class));
            to.setParentId(entity.getId());
        });

        to.setAssigneeId(from.getAssigneeId());
        to.setAssigneeName(mapperUtil.getUserName(
                Objects.requireNonNullElse(from.getAssigneeId(), 0L)));

        return to;
    }

    public List<TaskResponse> mapFromElasticModels(List<TaskElasticModel> from) {

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
