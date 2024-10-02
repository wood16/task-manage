package com.example.taskmanage.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {

    Long id;
    Long creatorId;
    LocalDateTime createDate;
    String type;
    String content;
    Long receiverId;
    String status;
    Long objectId;
}
