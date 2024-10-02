package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tbl_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long creatorId;
    LocalDateTime createDate;
    Long objectId;
    String type;
    String description;
    String fromValue;
    String toValue;
    String action;
    String field;

}
