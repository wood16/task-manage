package com.example.taskmanage.service.impl;

import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.model.TaskModel;
import com.example.taskmanage.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taskmanage.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskModel> getAllTask() {

        return taskMapper.mapModelsFromEntities(taskRepository.findAll());
    }
}
