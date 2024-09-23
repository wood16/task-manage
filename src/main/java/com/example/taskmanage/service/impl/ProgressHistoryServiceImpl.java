package com.example.taskmanage.service.impl;

import com.example.taskmanage.entity.ProgressHistoryEntity;
import com.example.taskmanage.repository.ProgressHistoryRepository;
import com.example.taskmanage.service.ProgressHistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProgressHistoryServiceImpl implements ProgressHistoryService {

    ProgressHistoryRepository progressHistoryRepository;

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
