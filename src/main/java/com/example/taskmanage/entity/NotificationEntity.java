package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tbl_notification")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long creatorId;
    LocalDateTime createDate;
    String type;
    String content;
    Long receiverId;
    String status;
    Long objectId;
}
