package com.example.taskmanage.service;

import com.example.taskmanage.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskService {

    Page<TaskDto> getAllTask(long userId,
                             String filter,
                             int page,
                             int pageSize,
                             String search,
                             String sortBy,
                             Sort.Direction sortOrder);

    TaskDto addTask(long userId, TaskDto taskModel);

    TaskDto patchTask(long userId, long taskId, TaskDto taskModel);

    TaskDto patchTaskUpdate(long userId, long taskId, TaskDto taskDto);

    TaskDto putTask(long userId, long taskId, TaskDto taskModel);

    TaskDto getTask(long taskId);

    Page<TaskDto> getChildTasks(long taskId, int page, int pageSize, String search, String sortBy, Sort.Direction sortOrder);

    void deleteTaskById(long userId, long taskId);

    void reindexAllTask();
}
