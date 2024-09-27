package com.example.taskmanage.service;

import com.example.taskmanage.dto.response.HistoryResponse;

import java.util.List;

public interface HistoryService {

    void addHistoryTask(long creatorId,
                        String type,
                        long objectId,
                        String action,
                        String field,
                        Object fromValue,
                        Object toValue);

    void reindexAllHistory();

    List<HistoryResponse> findByTypeAndObjectId(String type, long objectId);

    List<HistoryResponse> findByDate(String date);
}
