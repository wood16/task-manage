package com.example.taskmanage.service.impl;

import com.example.taskmanage.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.repository.HistoryRepository;
import com.example.taskmanage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryElasticRepository historyElasticRepository;

    @Override
    public void addHistory(long creatorId,
                           String type,
                           long objectId,
                           String description) {
        HistoryEntity entity = new HistoryEntity();

        entity.setCreatorId(creatorId);
        entity.setType(type);
        entity.setObjectId(objectId);
        entity.setDescription(description);

        saveEntity(entity);
    }

    private HistoryEntity saveEntity(HistoryEntity historyEntity) {

        HistoryEntity saved = historyRepository.save(historyEntity);

        historyElasticRepository.save(saved);

        return saved;
    }
}
