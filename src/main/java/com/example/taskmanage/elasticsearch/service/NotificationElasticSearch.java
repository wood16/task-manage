package com.example.taskmanage.elasticsearch.service;

import com.example.taskmanage.elasticsearch.keys.NotificationKeys;
import com.example.taskmanage.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationElasticSearch {

    @Autowired
    private SearchOperations searchOperations;

    public List<NotificationEntity> getNotifyOfUser(long userId) {

        Query query = NativeQuery.builder()
                .withQuery(
                        q -> q.bool(
                                b -> b.must(
                                        m -> m.term(
                                                t -> t.field(NotificationKeys.RECEIVER_ID)
                                                        .value(userId)
                                        )
                                )
                        )
                )
                .build();

        SearchHits<NotificationEntity> searchHits = searchOperations.search(query, NotificationEntity.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }
}
