package com.example.taskmanage.elarepository;

import com.example.taskmanage.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskElaRepository extends ElasticsearchRepository<TaskEntity, Long> {

    Page<TaskEntity> findByName(String name, Pageable pageable);

    List<TaskEntity> findByName(String name);
    List<TaskEntity> findAll();
}
