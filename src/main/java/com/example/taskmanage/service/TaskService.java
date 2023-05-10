package com.example.taskmanage.service;

import com.example.taskmanage.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Page<TaskDto> getAllTask(Pageable paging, String search);

    TaskDto addTask(long userId, TaskDto taskModel);

    TaskDto patchTask(long userId, long taskId, TaskDto taskModel);

    TaskDto putTask(long userId, long taskId, TaskDto taskModel);

    TaskDto getTask(long taskId);

    List<TaskDto> getChildTasks(long taskId);
}
