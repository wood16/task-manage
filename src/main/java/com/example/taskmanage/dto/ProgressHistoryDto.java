package com.example.taskmanage.dto;

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
public class ProgressHistoryDto {

    Long id;
    Long taskId;
    Long fromProgress;
    Long toProgress;
    String description;
    Long creatorId;
    Date createDate;
    String creatorName;
}
