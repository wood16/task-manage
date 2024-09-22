package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tbl_progress_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Long fromProgress;
    private Long toProgress;
    private String description;
    private Long creatorId;
    private Date createDate;
}
