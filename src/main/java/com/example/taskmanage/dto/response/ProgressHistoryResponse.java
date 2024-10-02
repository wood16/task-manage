package com.example.taskmanage.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgressHistoryResponse {

    Long id;
    Long taskId;
    Long fromProgress;
    Long toProgress;
    String description;
    Long creatorId;
    LocalDateTime createDate;
    String creatorName;
}
