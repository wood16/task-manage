package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.TaskExportDto;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskExportMapper {

    public TaskExportDto mapFromModel(TaskElasticModel from){

        TaskExportDto to = new TaskExportDto();

        to.setName(from.getName());
        to.setStatus(from.getStatus());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setAssignee(String.valueOf(from.getAssigneeId()));

        return to;
    }

    public TaskExportDto[] mapFromModels(List<TaskElasticModel> from){

        return from.stream().map(this::mapFromModel).toArray(TaskExportDto[]::new);
    }

}
