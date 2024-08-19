package com.example.taskmanage.elasticsearch.service;

import com.example.taskmanage.elasticsearch.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.elasticsearch.keys.HistoryKeys;
import com.example.taskmanage.elasticsearch.keys.TaskKeys;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
                                                t1 -> t1.field(HistoryKeys.TYPE).value(type)
                                        )
                                ).must(
                                        m2 -> m2.term(
                                                t2 -> t2.field(HistoryKeys.OBJECT_ID).value(objectId)
                                        )
                                )
                        )
                )
                .withPageable(PageRequest.of(0, 100))
                .withSort(Sort.by(Sort.Direction.DESC, TaskKeys.CREATE_DATE))
                .build();

        SearchHits<HistoryEntity> hits = elasticsearchOperations.search(query, HistoryEntity.class);

        return hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public List<HistoryEntity> getHistoryOfDate(String dateString) {

        LocalDate day = convertStringToDate(dateString, "dd/MM/yyyy");

        String startOfDay = String.valueOf(getMilliSecond(day.atStartOfDay()));
        String endOfDay = String.valueOf(getMilliSecond(getEndOfDay(day)));

        Query query = NativeQuery.builder()
                .withQuery(
                        q -> q.bool(
                                b -> b.must(
                                        m -> m.range(
                                                r -> r.field(TaskKeys.CREATE_DATE)
                                                        .from(startOfDay)
                                                        .to(endOfDay)
                                        )
                                )
                        )
                )
                .withPageable(PageRequest.of(0, 100))
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

    private LocalDate convertStringToDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return LocalDate.parse(dateString, formatter);
    }

    private LocalDateTime getEndOfDay(LocalDate localDate){

        return localDate.atTime(23, 59, 59, 999_999_999);
    }

    private long getMilliSecond(LocalDateTime localDateTime){
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        return instant.toEpochMilli();
    }
}
