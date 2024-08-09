package com.example.taskmanage.elasticsearch.service;

import com.example.taskmanage.elasticsearch.TaskKeys;
import com.example.taskmanage.elasticsearch.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryElasticSearch {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryElasticRepository historyElasticRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<HistoryEntity> getHistoryOfObject(String type, long objectId) {

        Query query = NativeQuery.builder()
                .withQuery(
                        q -> q.bool(
                            b -> b.must(
                                    m1 -> m1.term(
                                            t1 -> t1.field("type").value(type)
                                    )
                            ).must(
                                    m2 -> m2.term(
                                            t2 -> t2.field("objectId").value(objectId)
                                    )
                            )
                        )
                )
                .withSort(Sort.by(Sort.Direction.DESC, TaskKeys.CREATE_DATE))
                .build();

        SearchHits<HistoryEntity> hits = elasticsearchOperations.search(query, HistoryEntity.class);

        return hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public void reindexAllHistory() {

        historyElasticRepository.deleteAll();

        List<HistoryEntity> list = historyRepository.findAll();
        list.forEach(historyElasticRepository::save);
    }
}
