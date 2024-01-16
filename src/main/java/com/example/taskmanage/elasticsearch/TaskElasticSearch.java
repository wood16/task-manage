package com.example.taskmanage.elasticsearch;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.elasticrepository.TaskElasticRepository;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskElasticSearch {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private TaskElasticRepository taskElasticRepository;

    @Autowired
    private TaskRepository taskRepository;


    public Page<TaskEntity> searchByName(String searchTerm, Pageable pageable) {

        Query query = new StringQuery("{ \"match\": { \"name\": { \"query\": \"Jack\" } } } ");


        Query search = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m
                                .field("name")
                                .query(searchTerm)
                        )
                )
                .withPageable(pageable)
                .build();

        SearchHits<TaskEntity> hits = elasticsearchOperations.search(search, TaskEntity.class);

        return new PageImpl<>(
                hits.getSearchHits().stream().map(SearchHit::getContent).toList(),
                pageable,
                hits.getTotalHits());
    }

    public void reindexAllTask() {

        List<TaskEntity> taskEntities = taskRepository.findAll();

        taskEntities.forEach(taskElasticRepository::save);
    }
}
