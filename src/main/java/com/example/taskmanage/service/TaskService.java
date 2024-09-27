package com.example.taskmanage.service;

import com.example.taskmanage.dto.request.TaskRequest;
import com.example.taskmanage.dto.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface TaskService {

    Page<TaskResponse> getAllTask(long userId,
                                  String filter,
                                  int page,
                                  int pageSize,
                                  String search,
                                  String sortBy,
                                  Sort.Direction sortOrder);

    TaskResponse addTask(long userId, TaskRequest taskRequest);

    TaskResponse patchTask(long userId, long taskId, TaskRequest taskRequest);

    TaskResponse putTask(long userId, long taskId, TaskRequest taskRequest);

    TaskResponse getTask(long taskId);

    Page<TaskResponse> getChildTasks(long taskId, int page, int pageSize, String search, String sortBy, Sort.Direction sortOrder);

    void deleteTaskById(long userId, long taskId);

    void reindexAllTask();

    byte[] exportTask(long userId);
}
