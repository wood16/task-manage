package com.example.taskmanage.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryDto {

    Long id;
    Long creatorId;
    String creatorName;
    Date createDate;
    Long objectId;
    String type;
    String description;
    String fromValue;
    String toValue;
    String field;
    String action;
}
