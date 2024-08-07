package com.example.taskmanage.elasticsearch.elasticrepository;

import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskElasticRepository extends ElasticsearchRepository<TaskElasticModel, Long> {

    Page<TaskElasticModel> findByName(String name, Pageable pageable);
}
