package com.example.taskmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Date createDate;
    private Long creatorId;
    private String creatorName;
    private Date modifiedDate;
    private Long modifiedId;
    private String modifiedName;
    private Long parentId;
    private List<TaskDto> tasks;
}
