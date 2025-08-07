package com.example.taskmanage.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskExportResponse {

    String name;
    LocalDateTime createDate;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String priority;
    String status;
    String assignee;
}
