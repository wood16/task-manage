package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Entity
@Table(name = "tbl_task")
@Data
@Document(indexName = "tbl_task")
public class TaskEntity {

//    @table va @document phai co cung 'name' = 'indexName' de co the index

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    private Date startDate;
    private Date endDate;
    private String priority;
    private String description;
    private Long progress;
    private String progressType;
    private String status;
    private Long creatorId;
    private Date createDate;
    private Long modifiedId;
    private Date modifiedDate;
    private Long assigneeId;

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private TaskEntity parentTask;
}
