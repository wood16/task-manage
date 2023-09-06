package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tbl_task")
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private TaskEntity parentTask;
}
