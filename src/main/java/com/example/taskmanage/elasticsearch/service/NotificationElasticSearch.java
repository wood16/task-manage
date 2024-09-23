package com.example.taskmanage.elasticsearch.service;

import com.example.taskmanage.elasticsearch.elasticrepository.NotificationElasticRepository;
import com.example.taskmanage.elasticsearch.keys.NotificationKeys;
import com.example.taskmanage.elasticsearch.keys.TaskKeys;
import com.example.taskmanage.entity.NotificationEntity;
import com.example.taskmanage.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationElasticSearch {

    SearchOperations searchOperations;
    NotificationElasticRepository notificationElasticRepository;
    NotificationRepository notificationRepository;

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
                .withSort(Sort.by(Sort.Direction.DESC, TaskKeys.CREATE_DATE))
                .build();

        SearchHits<NotificationEntity> searchHits = searchOperations.search(query, NotificationEntity.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public void reindexAllNotification(){

        notificationElasticRepository.deleteAll();

        notificationRepository.findAll().stream().parallel().forEach(notificationElasticRepository::save);
    }
}
