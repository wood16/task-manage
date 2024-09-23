package com.example.taskmanage.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDto {

    Long id;
    Long creatorId;
    Date createDate;
    String type;
    String content;
    Long receiverId;
    String status;
    Long objectId;
}
