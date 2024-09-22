package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Entity
@Table(name = "tbl_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Long objectId;
}
