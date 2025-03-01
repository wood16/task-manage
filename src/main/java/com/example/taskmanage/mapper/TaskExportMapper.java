package com.example.taskmanage.mapper;

import com.example.taskmanage.dto.response.TaskExportResponse;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TaskExportMapper {

    public TaskExportResponse mapFromModel(TaskElasticModel from) {

        TaskExportResponse to = new TaskExportResponse();

        to.setName(from.getName());
        to.setStatus(from.getStatus());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setPriority(from.getPriority());
        to.setAssignee(String.valueOf(from.getAssigneeId()));

        return to;
    }

    public TaskExportResponse[] mapFromModels(List<TaskElasticModel> from) {

        return from.stream().map(this::mapFromModel).toArray(TaskExportResponse[]::new);
    }

    private LocalDateTime mapToLocalDateTime(Date from) {

        return LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
    }
}
