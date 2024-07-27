package com.example.taskmanage.service;

public interface HistoryService {

    void addHistory(long creatorId,
                    String type,
                    long objectId,
                    String description);
}
