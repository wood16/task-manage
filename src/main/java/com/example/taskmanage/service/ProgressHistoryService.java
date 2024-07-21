package com.example.taskmanage.service;

public interface ProgressHistoryService {

    void addProgressHistory(long userId, long taskId, long progress, String description);
}
