package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Entity
@Table(name = "tbl_notification")
@Data
@Document(indexName = "tbl_notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long creatorId;
    private Date createDate;
    private String type;
    private String content;
    private Long receiverId;
    private String status;
}
