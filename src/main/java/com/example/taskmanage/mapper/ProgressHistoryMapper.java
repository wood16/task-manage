package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.ProgressHistoryDto;
import com.example.taskmanage.entity.ProgressHistoryEntity;
import com.example.taskmanage.repository.ProgressHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProgressHistoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapperUtil mapperUtil;

    @Autowired
    private ProgressHistoryRepository progressHistoryRepository;

    public ProgressHistoryDto mapFromEntity(ProgressHistoryEntity from) {

        ProgressHistoryDto to = modelMapper.map(from, ProgressHistoryDto.class);

        to.setCreatorName(mapperUtil.getUserName(from.getCreatorId()));

        return to;
    }

    public List<ProgressHistoryDto> mapFromEntities(List<ProgressHistoryEntity> from) {

        return from.stream().map(this::mapFromEntity).toList();
    }

    public List<ProgressHistoryDto> mapFromTaskId(long taskId){

        return progressHistoryRepository.findByTaskId(taskId)
                .stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(ProgressHistoryDto::getCreateDate).reversed())
                .toList();
    }
}
