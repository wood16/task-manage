package com.example.taskmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressHistoryDto {

    private Long id;
    private Long taskId;
    private Long fromProgress;
    private Long toProgress;
    private String description;
    private Long creatorId;
    private Date createDate;
    private String createName;
}
