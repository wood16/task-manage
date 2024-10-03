package com.example.taskmanage.elasticsearch.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "tbl_task")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskElasticModel {

    Long id;
    String name;
    Date startDate;
    Date endDate;
    String priority;
    String description;
    Long progress;
    String status;
    Long creatorId;
    Date createDate;
    Long modifiedId;
    Date modifiedDate;
    Long assigneeId;
    Long priorityNumber;

    TaskElasticModel parentTask;
}
