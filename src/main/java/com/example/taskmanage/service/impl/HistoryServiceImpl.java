package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.mapper.CommonMapper;
import com.example.taskmanage.repository.HistoryRepository;
import com.example.taskmanage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryElasticRepository historyElasticRepository;

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void addHistory(long creatorId,
                           String type,
                           long objectId,
                           String action,
                           String detail) {
        HistoryEntity entity = new HistoryEntity();

        entity.setCreatorId(creatorId);
        entity.setCreateDate(new Date());
        entity.setType(type);
        entity.setObjectId(objectId);
        entity.setDescription(mapDescription(type, action, detail));

        saveEntity(entity);
    }

    @Override
    public List<HistoryDto> findByTypeAndObjectId(String type, long objectId) {

        return commonMapper.mapList(historyRepository.findByTypeAndObjectId(type, objectId), HistoryDto.class);
    }

    private String mapAction(String action, String detail) {

        return switch (action) {
            case "create" -> "Tạo mới ";
            case "update" -> "Cập nhật ";
            case "delete" -> "Xóa ";
            default -> "";
        } + detail;
    }

    private String mapType(String type) {
        if (type.equals("task"))
            return " công việc";

        return "";
    }

    private String mapDescription(String type, String action, String detail) {

        return mapAction(action, detail) + mapType(type);
    }

    private HistoryEntity saveEntity(HistoryEntity historyEntity) {

        HistoryEntity saved = historyRepository.save(historyEntity);

        historyElasticRepository.save(saved);

        return saved;
    }
}
