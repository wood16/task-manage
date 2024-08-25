package com.example.taskmanage.elasticsearch.elasticrepository;

import com.example.taskmanage.entity.NotificationEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationElasticRepository extends ElasticsearchRepository<NotificationEntity, Long> {
}
