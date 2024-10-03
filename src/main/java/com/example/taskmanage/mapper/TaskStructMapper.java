package com.example.taskmanage.mapper;

import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskStructMapper {

    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    TaskElasticModel mapForIndex(TaskEntity from);
}
