package com.example.taskmanage.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskExportDto {

    String name;
    Date startDate;
    Date endDate;
    String priority;
    String status;
    String assignee;
}
