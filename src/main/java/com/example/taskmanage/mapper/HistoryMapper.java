package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.entity.HistoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryMapper {

    @Autowired
    private MapperUtil mapperUtil;

    @Autowired
    private ModelMapper modelMapper;

    public HistoryDto mapFromEntity(HistoryEntity from) {

        HistoryDto to = modelMapper.map(from, HistoryDto.class);

        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));

        return to;
    }

    public List<HistoryDto> mapFromEntities(List<HistoryEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }
}
