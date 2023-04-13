package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Page<TaskDto> getAllTask(Pageable paging) {

        List<TaskDto> taskModels = taskMapper.mapModelsFromEntities(taskRepository.findAll(paging).getContent());

        return new PageImpl<>(taskModels);
    }

    @Override
    public TaskDto addTask(long userId, TaskDto taskDto) {

        TaskEntity taskEntity = taskMapper.mapEntityFromModel(taskDto);
        setCreateInfo(userId, taskEntity);

        taskEntity.setStatus("pending");
        taskEntity.setProgress(0L);

        return taskMapper.mapModelFromEntity(taskRepository.save(taskEntity));
    }

    @Override
    public TaskDto patchTask(long userId, long taskId, TaskDto taskDto) {

        if (Objects.nonNull(taskDto.getProgress())) {
            updateProgress(userId, taskId, taskDto.getProgress());
        }

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);

        if (taskEntity.isPresent())
            return taskMapper.mapModelFromEntity(taskEntity.get());

        return new TaskDto();
    }

    private void updateProgress(long userId, long taskId, long progress) {

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);

        if (taskEntity.isPresent()) {
            setModifiedInfo(userId, taskEntity.get());
            taskEntity.get().setProgress(progress);

            taskRepository.save(taskEntity.get());
        }
    }

    private void setCreateInfo(long creatorId, TaskEntity taskEntity) {
        taskEntity.setCreatorId(creatorId);
        taskEntity.setCreateDate(new Date());

        setModifiedInfo(creatorId, taskEntity);
    }

    private void setModifiedInfo(long modifiedId, TaskEntity taskEntity) {
        taskEntity.setModifiedId(modifiedId);
        taskEntity.setModifiedDate(new Date());
    }
}
