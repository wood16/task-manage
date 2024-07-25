package com.example.taskmanage.service;

public interface ProgressHistoryService {

    void addProgressHistory(long userId,
                            long taskId,
                            long fromProgress,
                            long toProgress,
                            String description);
}
