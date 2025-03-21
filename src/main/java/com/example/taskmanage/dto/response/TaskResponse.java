package com.example.taskmanage.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {

    Long id;
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String priority;
    String description;
    Long progress;
    String status;
    LocalDateTime createDate;
    Long creatorId;
    String creatorName;
    LocalDateTime modifiedDate;
    Long modifiedId;
    String modifiedName;
    Long parentId;
    List<TaskResponse> tasks;
    TaskResponse parentTask;
    Long assigneeId;
    String assigneeName;
    List<ProgressHistoryResponse> progressHistories;
}
