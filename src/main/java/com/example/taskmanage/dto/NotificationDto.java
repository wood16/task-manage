package com.example.taskmanage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {

    private Long id;
    private Long creatorId;
    private Date createDate;
    private String type;
    private String content;
    private Long receiverId;
    private String status;
}
