package com.example.taskmanage.service;

import com.example.taskmanage.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskDto> getAllTask(Pageable paging);

    TaskDto addTask(TaskDto taskModel);

    TaskDto patchTask(long taskId, TaskDto taskModel);
}
