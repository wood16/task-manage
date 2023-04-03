package com.example.taskmanage.service;

import com.example.taskmanage.model.TaskModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskModel> getAllTask(Pageable paging);

    TaskModel addTask(TaskModel taskModel);
}
