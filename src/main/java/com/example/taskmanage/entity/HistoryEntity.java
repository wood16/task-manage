package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Entity
@Table(name = "tbl_history")
@Data
@Document(indexName = "tbl_history")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long creatorId;
    private Date createDate;
    private Long objectId;
    private String type;
    private String description;
    private String fromValue;
    private String toValue;

}
