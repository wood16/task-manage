package com.example.taskmanage.dto.request;

import com.example.taskmanage.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "TASK_NAME_NULL")
    @NameConstraint(min = 5, message = "INVALID_TASK_NAME")
    String name;
    @NotNull(message = "START_DATE_TASK_NAME")
    Date startDate;
    @NotNull(message = "END_DATE_TASK_NAME")
    Date endDate;
    String priority;
    String description;
    Long parentId;
    @NotNull(message = "TASK_ASSIGN_NULL")
    Long assigneeId;
    Long progress;
    String status;
}
