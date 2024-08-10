package com.example.taskmanage.elasticsearch.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "tbl_task")
public class TaskElasticModel {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String priority;
    private String description;
    private Long progress;
    private String status;
    private Long creatorId;
    private Date createDate;
    private Long modifiedId;
    private Date modifiedDate;
    private Long assigneeId;
    private Long priorityNumber;

    private TaskElasticModel parentTask;
}
