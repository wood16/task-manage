package com.example.taskmanage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    Long id;
    @NotNull
    String name;
    Date startDate;
    Date endDate;
    String priority;
    String description;
    Long progress;
    String status;
    Date createDate;
    Long creatorId;
    String creatorName;
    Date modifiedDate;
    Long modifiedId;
    String modifiedName;
    Long parentId;
    List<TaskDto> tasks;
    TaskDto parentTask;
    Long assigneeId;
    String assigneeName;
    List<ProgressHistoryDto> progressHistories;
}
