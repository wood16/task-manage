package com.example.taskmanage.elasticsearch.elasticrepository;

import com.example.taskmanage.entity.HistoryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryElasticRepository extends ElasticsearchRepository<HistoryEntity, Long> {
}
