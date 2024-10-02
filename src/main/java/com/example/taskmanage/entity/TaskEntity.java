package com.example.taskmanage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Document(indexName = "tbl_task")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEntity {

//    @table va @document phai co cung 'name' = 'indexName' de co the index

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    TaskEntity parentTask;
}
