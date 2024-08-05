package com.example.taskmanage.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.elasticrepository.TaskElasticRepository;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.service.UserService;
import org.modelmapper.ModelMapper;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskElasticSearch {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private TaskElasticRepository taskElasticRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    public Page<TaskEntity> getMyTask(long userId, String searchTerm, Pageable pageable) {

        boolean isAdmin = userService.checkUserRole(userId, "admin");

        BoolQuery searchQuery = BoolQuery.of(
                b -> b.must(
                        m -> m.wildcard(
                                w -> w.field(TaskKeys.NAME)
                                        .wildcard("*".concat(
                                                Objects.requireNonNullElse(searchTerm, "")).concat("*"))
                        )
                )
        );

        BoolQuery ownQuery = BoolQuery.of(
                b -> b.should(s1 -> s1.term(
                                t1 -> t1.field(TaskKeys.CREATOR_ID).value(userId)
                        )
                ).should(
                        s2 -> s2.term(
                                t2 -> t2.field(TaskKeys.ASSIGNEE_ID).value(userId)
                        )
                )
        );

        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(
                        isAdmin ?
                                b -> b.must(
                                        m1 -> m1.bool(searchQuery))
                                :
                                b -> b.must(
                                        m1 -> m1.bool(ownQuery)
                                ).must(
                                        m2 -> m2.bool(searchQuery)
                                )
                ))
                .withPageable(pageable)
                .build();

        SearchHits<TaskEntity> hits = elasticsearchOperations.search(query, TaskEntity.class);

        return new PageImpl<>(
                hits.getSearchHits().stream().map(SearchHit::getContent).toList(),
                pageable,
                hits.getTotalHits());
    }

    public Page<TaskEntity> searchByName(String searchTerm, Pageable pageable) {
        //text field can not sort

        Query query = new StringQuery("{ \"match\": { \"name\": { \"query\": \"Jack\" } } } ");

        Query search = NativeQuery.builder()
                .withQuery(p -> p
                        .bool(b -> b
                                .should(s -> s
                                        .bool(b1 -> b1
                                                .must(w -> w
                                                        .wildcard(s1 -> s1
                                                                .field(TaskKeys.NAME)
                                                                .wildcard("*"
                                                                        .concat(Objects.requireNonNullElse(searchTerm, ""))
                                                                        .concat("*"))
                                                        )
                                                )
                                        )
                                )
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

    public Page<TaskEntity> searchByStartDate(Date startDate, Pageable pageable) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Query search = NativeQuery.builder()
                .withQuery(q -> q
                        .range(m -> m
                                .field("startDate")
                                .from("1680557640000")
                                .to(String.valueOf(startDate.getTime()))
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

    public Page<TaskDto> getChildTasks(long id,
                                       Pageable pageable,
                                       String search) {


        co.elastic.clients.elasticsearch._types.query_dsl.Query searchQuery =
                new co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder()
                        .bool(b1 -> b1
                                .must(m1 -> m1
                                        .wildcard(w1 -> w1
                                                .field("name")
                                                .wildcard(search.concat("*"))
                                        )
                                )
                        )
                        .build();

        Query searchChildTask = NativeQuery.builder()
                .withQuery(q -> q
                                .bool(b1 -> b1
                                                .must(searchQuery)
//                                .must(m1 -> m1
//                                        .wildcard(w1 -> w1
//                                                .field("name")
//                                                .wildcard(
//                                                        Objects.nonNull(search) ? search.concat("*") : "")
//                                        )
//                                )
                                                .filter(f1 -> f1
                                                        .term(t1 -> t1
                                                                .field("parentTask.id")
                                                                .value(id)
                                                        )
                                                )

                                )
                )
//                .withSort()
                .withPageable(pageable)
                .build();

        SearchHits<TaskEntity> hits = elasticsearchOperations.search(searchChildTask, TaskEntity.class);

        return new PageImpl<>(
                hits.stream().map(hit -> modelMapper.map(hit.getContent(), TaskDto.class)).toList(),
                pageable,
                hits.getTotalHits());
    }

    public void reindexAllTask() {

        taskElasticRepository.deleteAll();

        List<TaskEntity> taskEntities = taskRepository.findAll();
        taskEntities.forEach(taskElasticRepository::save);
    }
}
