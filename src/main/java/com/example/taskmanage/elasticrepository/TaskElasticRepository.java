package com.example.taskmanage.elasticrepository;

import com.example.taskmanage.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskElasticRepository extends ElasticsearchRepository<TaskEntity, Long> {

    Page<TaskEntity> findByName(String name, Pageable pageable);
}
