package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.mapper.HistoryMapper;
import com.example.taskmanage.repository.HistoryRepository;
import com.example.taskmanage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryElasticRepository historyElasticRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public void addHistoryTask(long creatorId,
                               String type,
                               long objectId,
                               String action,
                               String field,
                               Object fromValue,
                               Object toValue) {
        HistoryEntity entity = new HistoryEntity();

        entity.setCreatorId(creatorId);
        entity.setCreateDate(new Date());
        entity.setType(type);
        entity.setObjectId(objectId);
        entity.setDescription(mapDescription(type, action, field));
        entity.setAction(action);
        entity.setField(field);
        entity.setFromValue(Objects.requireNonNullElse(fromValue, "").toString());
        entity.setToValue(Objects.requireNonNullElse(toValue, "").toString());

        saveEntity(entity);
    }

    @Override
    public List<HistoryDto> findByTypeAndObjectId(String type, long objectId) {

        return historyMapper.mapFromEntities(
                historyRepository.findByTypeAndObjectId(type, objectId)
                        .stream()
                        .sorted(Comparator.comparing(HistoryEntity::getCreateDate).reversed())
                        .toList());
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
            return "công việc";

        return "";
    }

    private String mapField(String field){

        return switch (field){
            case "name" -> "tên công việc ";
            case "description" -> "mô tả ";
            case "priority" -> "độ ưu tiên ";
            case "status" -> "trạng thái ";
            case "startDate" -> "ngày bắt đầu ";
            case "endDate" -> "ngày kết thúc ";
            case "assigneeId" -> "người thực hiện ";
            case "progress" -> "tiến độ ";
            default -> "";
        };
    }

    private String mapDescription(String type, String action, String field) {

        return mapAction(action, mapField(field)) + mapType(type);
    }

    private HistoryEntity saveEntity(HistoryEntity historyEntity) {

        HistoryEntity saved = historyRepository.save(historyEntity);

        historyElasticRepository.save(saved);

        return saved;
    }
}
