package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.response.ProgressHistoryResponse;
import com.example.taskmanage.entity.ProgressHistoryEntity;
import com.example.taskmanage.repository.ProgressHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProgressHistoryMapper {

    ModelMapper modelMapper;
    MapperUtil mapperUtil;
    ProgressHistoryRepository progressHistoryRepository;

    public ProgressHistoryResponse mapFromEntity(ProgressHistoryEntity from) {

        ProgressHistoryResponse to = modelMapper.map(from, ProgressHistoryResponse.class);

        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));

        return to;
    }

    public List<ProgressHistoryResponse> mapFromEntities(List<ProgressHistoryEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }

    public List<ProgressHistoryResponse> mapFromTaskId(long taskId) {

        return progressHistoryRepository.findByTaskId(taskId)
                .stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(ProgressHistoryResponse::getCreateDate).reversed())
                .toList();
    }
}
