package com.example.taskmanage.mapper;

import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStructMapper {

    TaskElasticModel mapForIndex(TaskEntity from);
}
