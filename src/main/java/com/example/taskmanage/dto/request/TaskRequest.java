package com.example.taskmanage.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {

    String name;
    Date startDate;
    Date endDate;
    String priority;
    String description;
    Long parentId;
    Long assigneeId;
    Long progress;
    String status;
}
