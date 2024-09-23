package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.entity.HistoryEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryMapper {

    MapperUtil mapperUtil;
    ModelMapper modelMapper;

    public HistoryDto mapFromEntity(HistoryEntity from) {

        HistoryDto to = modelMapper.map(from, HistoryDto.class);

        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));

        return to;
    }

    public List<HistoryDto> mapFromEntities(List<HistoryEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }
}
