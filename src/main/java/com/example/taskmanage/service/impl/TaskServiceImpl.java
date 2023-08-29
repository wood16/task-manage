package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Page<TaskDto> getAllTask(Pageable paging, String search) {

        List<TaskDto> taskModels;

        if (Objects.nonNull(search))
            taskModels =
                    taskMapper.mapModelsFromEntities(taskRepository.findByNameContaining(search, paging).getContent());
        else
            taskModels =
                    taskMapper.mapModelsFromEntities(taskRepository.findAll(paging).getContent());


        return new PageImpl<>(taskModels);
    }

    @Override
    public TaskDto addTask(long userId, TaskDto taskDto) {

        TaskEntity taskEntity = taskMapper.mapEntityFromModel(taskDto, new TaskEntity());
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

        if (Objects.nonNull(taskDto.getStartDate()) && Objects.nonNull(taskDto.getEndDate())) {
            updateDate(userId, taskId, taskDto.getStartDate(), taskDto.getEndDate());
        }

        if (Objects.nonNull(taskDto.getDescription())) {
            updateDescription(userId, taskId, taskDto.getDescription());
        }

        return taskRepository.findById(taskId)
                .map(task -> taskMapper.mapModelFromEntity(task))
                .orElse(new TaskDto());
    }

    @Override
    public TaskDto putTask(long userId, long taskId, TaskDto taskDto) {

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        TaskEntity newTaskEntity = new TaskEntity();


        if (taskEntity.isPresent()) {
            newTaskEntity = taskMapper.mapEntityFromModel(taskDto, taskEntity.get());
            setModifiedInfo(userId, newTaskEntity);

            return taskMapper.mapModelFromEntity(taskRepository.save(newTaskEntity));
        }

        return taskMapper.mapModelFromEntity(newTaskEntity);
    }

    @Override
    public TaskDto getTask(long taskId) {

        return taskRepository.findById(taskId)
                .map(task -> taskMapper.mapModelFromEntity(task))
                .orElseThrow(() ->
                        new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Task not found"));
    }

    @Override
    public List<TaskDto> getChildTasks(long taskId) {

        return taskMapper.mapModelsFromEntities(taskRepository.findByParentId(taskId));
    }

    public String getTaskById(Long id){

        return getTaskEntity(id, TaskEntity::getName);
    }

    private <T> T getTaskEntity(Long id, Function<TaskEntity, T> taskFunction){

        Optional<TaskEntity> taskEntity = taskRepository.findById(id);

        return taskFunction.apply(taskEntity.get());
    }

    private void updateProgress(long userId, long taskId, long progress) {

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);

        if (taskEntity.isPresent()) {
            setModifiedInfo(userId, taskEntity.get());
            taskEntity.get().setProgress(progress);

            taskRepository.save(taskEntity.get());
        }
    }

    private void updateDate(long userId, long taskId, Date startDate, Date endDate) {

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);

        if (taskEntity.isPresent()) {
            setModifiedInfo(userId, taskEntity.get());
            taskEntity.get().setStartDate(startDate);
            taskEntity.get().setEndDate(endDate);

            taskRepository.save(taskEntity.get());
        }
    }

    private void updateDescription(long userId, long taskId, String description) {

        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);

        if (taskEntity.isPresent()) {
            setModifiedInfo(userId, taskEntity.get());
            taskEntity.get().setDescription(description);

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
