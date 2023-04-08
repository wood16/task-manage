package com.example.taskmanage.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class TaskDto {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String priority;
    private String description;
    private Long progress;
    private String progressType;
    private String status;
}
