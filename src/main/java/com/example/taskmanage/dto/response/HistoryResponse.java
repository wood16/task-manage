package com.example.taskmanage.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryResponse {

    Long id;
    Long creatorId;
    String creatorName;
    LocalDateTime createDate;
    Long objectId;
    String type;
    String description;
    String fromValue;
    String toValue;
    String field;
    String action;
}
