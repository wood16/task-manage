package com.example.taskmanage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryDto {

    private Long id;
    private Long creatorId;
    private String creatorName;
    private Date createDate;
    private Long objectId;
    private String type;
    private String description;
    private String fromValue;
    private String toValue;
}
