package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.response.HistoryResponse;
import com.example.taskmanage.entity.HistoryEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryMapper {

    MapperUtil mapperUtil;
    ModelMapper modelMapper;

    public HistoryResponse mapFromEntity(HistoryEntity from) {

        HistoryResponse to = modelMapper.map(from, HistoryResponse.class);

        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));

        return to;
    }

    public List<HistoryResponse> mapFromEntities(List<HistoryEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }
}
