package com.example.taskmanage.elasticsearch.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "tbl_task")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskElasticModel {

    Long id;
    @Field(type = FieldType.Keyword)
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
