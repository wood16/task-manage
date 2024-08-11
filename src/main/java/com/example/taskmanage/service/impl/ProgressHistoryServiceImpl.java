package com.example.taskmanage.service.impl;

import com.example.taskmanage.entity.ProgressHistoryEntity;
import com.example.taskmanage.repository.ProgressHistoryRepository;
import com.example.taskmanage.service.ProgressHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProgressHistoryServiceImpl implements ProgressHistoryService {

    @Autowired
    private ProgressHistoryRepository progressHistoryRepository;

    @Override
    public void addProgressHistory(long userId,
                                   long taskId,
                                   long fromProgress,
                                   long toProgress,
                                   String description) {

        ProgressHistoryEntity entity = new ProgressHistoryEntity();

        entity.setTaskId(taskId);
        entity.setCreatorId(userId);
        entity.setFromProgress(fromProgress);
        entity.setToProgress(toProgress);
        entity.setDescription(description);
        entity.setCreateDate(new Date());

        progressHistoryRepository.save(entity);
    }
}
