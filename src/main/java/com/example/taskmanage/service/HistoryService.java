package com.example.taskmanage.service;

import com.example.taskmanage.dto.HistoryDto;

import java.util.List;

public interface HistoryService {

    void addHistory(long creatorId,
                    String type,
                    long objectId,
                    String action,
                    String detail);

    List<HistoryDto> findByTypeAndObjectId(String type, long objectId);
}
