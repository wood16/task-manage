package com.example.taskmanage.repository;

import com.example.taskmanage.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends ElasticsearchRepository<TaskEntity, Long> {

    Page<TaskEntity> findAll(Pageable pageable);

    Page<TaskEntity> findByNameContaining(String name, Pageable pageable);

    List<TaskEntity> findByParentTask_Id(Long id);
}
