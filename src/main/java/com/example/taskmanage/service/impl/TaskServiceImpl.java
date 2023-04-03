package com.example.taskmanage.service.impl;

import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.model.TaskModel;
import com.example.taskmanage.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.taskmanage.service.TaskService;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Page<TaskModel> getAllTask(Pageable paging) {

        List<TaskModel> taskModels = taskMapper.mapModelsFromEntities(taskRepository.findAll(paging).getContent());

        return new PageImpl<>(taskModels);
    }

    @Override
    public TaskModel addTask(TaskModel taskModel) {

        TaskEntity taskEntity = taskMapper.mapEntityFromModel(taskModel);
        setCreateInfo(0L, taskEntity);

        taskEntity.setProgress(0L);

        return taskMapper.mapModelFromEntity(taskRepository.save(taskEntity));
    }

    private void setCreateInfo(long creatorId, TaskEntity taskEntity){
        taskEntity.setCreatorId(creatorId);
        taskEntity.setCreateDate(new Date());

        setModifiedInfo(creatorId, taskEntity);
    }

    private void setModifiedInfo(long modifiedId, TaskEntity taskEntity){
        taskEntity.setModifiedId(modifiedId);
        taskEntity.setModifiedDate(new Date());
    }
}
