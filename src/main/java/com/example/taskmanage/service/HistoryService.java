package com.example.taskmanage.service;

import com.example.taskmanage.dto.HistoryDto;

import java.util.List;

public interface HistoryService {

    void addHistoryTask(long creatorId,
                        String type,
                        long objectId,
                        String action,
                        String field,
                        Object fromValue,
                        Object toValue);

    List<HistoryDto> findByTypeAndObjectId(String type, long objectId);
}
