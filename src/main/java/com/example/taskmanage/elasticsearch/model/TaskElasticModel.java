package com.example.taskmanage.elasticsearch.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(indexName = "tbl_task")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskElasticModel {

    Long id;
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String priority;
    String description;
    Long progress;
    String status;
    Long creatorId;
    LocalDateTime createDate;
    Long modifiedId;
    LocalDateTime modifiedDate;
    Long assigneeId;
    Long priorityNumber;

    TaskElasticModel parentTask;
}
