package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tbl_progress_history")
@Data
public class ProgressHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Long progress;
    private String description;
    private Long creatorId;
    private Date createDate;
}
