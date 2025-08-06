package com.example.taskmanage.elasticsearch.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.elasticsearch.elasticrepository.TaskElasticRepository;
import com.example.taskmanage.elasticsearch.keys.TaskKeys;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskElasticSearch {

    ElasticsearchOperations elasticsearchOperations;
    TaskElasticRepository taskElasticRepository;
    TaskRepository taskRepository;
    ModelMapper modelMapper;
    UserService userService;
    TaskMapper taskMapper;

    public Page<TaskElasticModel> getMyTask(String type,
                                            long userId,
                                            String searchTerm,
                                            Pageable pageable,
                                            Map<String, Object> filters) {

        boolean isAdmin = userService.checkUserRole(userId, "admin");

//        Cách 2
        List<Query> mustQueries = new ArrayList<>();
        List<Query> shouldQueries = new ArrayList<>();

        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();

        // 1. Filter conditions
        if (Objects.nonNull(filters)) {
//            mustQueries.addAll(buildFilterQueries(filters));

            boolBuilder.filter(buildFilterQueries(filters));
        }

        // 2. Search by name
        if (StringUtils.hasText(searchTerm)) {

            mustQueries.add(WildcardQuery.of(
                    w -> w.field(TaskKeys.NAME)
                            .wildcard("*".concat(searchTerm).concat("*")).caseInsensitive(true))._toQuery());
        }

        // 3. Role-based access control
        if (!isAdmin) {

            if (Objects.nonNull(type) && type.equals("assignee")) {
                shouldQueries.add(TermQuery.of(t -> t.field(TaskKeys.ASSIGNEE_ID).value(userId))._toQuery());
            } else {
                shouldQueries.add(TermQuery.of(t -> t.field(TaskKeys.CREATOR_ID).value(userId))._toQuery());
                shouldQueries.add(TermQuery.of(t -> t.field(TaskKeys.ASSIGNEE_ID).value(userId))._toQuery());
            }

            mustQueries.add(BoolQuery.of(b -> b.should(shouldQueries))._toQuery());
        }

        boolBuilder.must(mustQueries);
        Query tempQuery = boolBuilder.build()._toQuery();

        org.springframework.data.elasticsearch.core.query.Query query = NativeQuery.builder()
                .withQuery(tempQuery)
//                .withQuery(q -> q.bool(b -> b.must(mustQueries)))
                .withPageable(pageable)
                .build();

//          Cách 1
//        List<Query> filterQueries =
//                filters != null ? buildFilterQueries(filters) : List.of();
//
//        BoolQuery searchQuery = BoolQuery.of(
//                b -> b.must(
//                        m -> m.wildcard(
//                                w -> w.field(TaskKeys.NAME)
//                                        .wildcard("*".concat(
//                                                Objects.requireNonNullElse(searchTerm, "")).concat("*"))
//                                        .caseInsensitive(true)
//                        )
//                ).must(filterQueries)
//        );
//
//        BoolQuery ownQuery = Objects.nonNull(type) && type.equals("assignee") ? BoolQuery.of(
//                b -> b.should(
//                        s1 -> s1.term(
//                                t1 -> t1.field(TaskKeys.ASSIGNEE_ID).value(userId))
//                )
//        ) : BoolQuery.of(
//                b -> b.should(s1 -> s1.term(
//                                t1 -> t1.field(TaskKeys.CREATOR_ID).value(userId)
//                        )
//                ).should(
//                        s2 -> s2.term(
//                                t2 -> t2.field(TaskKeys.ASSIGNEE_ID).value(userId)
//                        )
//                )
//        );
//
//        org.springframework.data.elasticsearch.core.query.Query query = NativeQuery.builder()
//                .withQuery(q -> q.bool(
//                        isAdmin ?
//                                b -> b.must(
//                                        m1 -> m1.bool(searchQuery))
//                                :
//                                b -> b.must(
//                                        m1 -> m1.bool(ownQuery)
//                                ).must(
//                                        m2 -> m2.bool(searchQuery)
//                                )
//                ))
//                .withPageable(pageable)
//                .build();

        SearchHits<TaskElasticModel> hits = elasticsearchOperations.search(query, TaskElasticModel.class);

        return new PageImpl<>(
                hits.getSearchHits().stream().map(SearchHit::getContent).toList(),
                pageable,
                hits.getTotalHits());
    }

    private List<Query> buildFilterQueries(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) return List.of();

        List<Query> queries = new ArrayList<>();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey(); // ví dụ: "name.like"
            Object value = entry.getValue(); // ví dụ: "Lam" hoặc List.of("NEW", "IN_PROGRESS")

            String[] parts = key.split("\\.");
            if (parts.length != 2) continue;

            String field = parts[0];
            String operator = parts[1];

            switch (operator) {
                case "eq":
                    queries.add(TermQuery.of(t -> t.field(field).value(value.toString()))._toQuery());
                    break;
                case "in":
                    if (value instanceof Collection<?> list) {
                        List<FieldValue> values = list.stream()
                                .map(Object::toString)
                                .map(FieldValue::of)
                                .toList();
                        queries.add(TermsQuery.of(
                                t -> t.field(field).terms(ts -> ts.value(values)))._toQuery());
                    }
                    break;
                case "like":
                    queries.add(WildcardQuery.of(
                            w -> w.field(field).wildcard("*" + value + "*").caseInsensitive(true))._toQuery());
                    break;
                case "gt":
                    queries.add(RangeQuery.of(r -> r.field(field).gt(JsonData.of(value)))._toQuery());
                    break;
                case "lt":
                    queries.add(RangeQuery.of(r -> r.field(field).lt(JsonData.of(value)))._toQuery());
                    break;
                // Bạn có thể mở rộng thêm ở đây
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }

        return queries;
    }

    public Page<TaskElasticModel> searchByName(String searchTerm, Pageable pageable) {
        //text field can not sort

        org.springframework.data.elasticsearch.core.query.Query query =
                new StringQuery("{ \"match\": { \"name\": { \"query\": \"Jack\" } } } ");

        org.springframework.data.elasticsearch.core.query.Query search = NativeQuery.builder()
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

        SearchHits<TaskElasticModel> hits = elasticsearchOperations.search(search, TaskElasticModel.class);

        return new PageImpl<>(
                hits.getSearchHits().stream().map(SearchHit::getContent).toList(),
                pageable,
                hits.getTotalHits());
    }

    public Page<TaskElasticModel> searchByStartDate(Date startDate, Pageable pageable) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        org.springframework.data.elasticsearch.core.query.Query search = NativeQuery.builder()
                .withQuery(q -> q
                        .range(m -> m
                                .field("startDate")
                                .from("1680557640000")
                                .to(String.valueOf(startDate.getTime()))
                        )
                )
                .withPageable(pageable)
                .build();

        SearchHits<TaskElasticModel> hits = elasticsearchOperations.search(search, TaskElasticModel.class);

        return new PageImpl<>(
                hits.getSearchHits().stream().map(SearchHit::getContent).toList(),
                pageable,
                hits.getTotalHits());
    }

    public Page<TaskResponse> getChildTasks(long id,
                                            Pageable pageable,
                                            String search) {


        Query searchQuery =
                new Query.Builder()
                        .bool(b1 -> b1
                                .must(m1 -> m1
                                        .wildcard(w1 -> w1
                                                .field("name")
                                                .wildcard(search.concat("*"))
                                        )
                                )
                        )
                        .build();

        org.springframework.data.elasticsearch.core.query.Query searchChildTask = NativeQuery.builder()
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

        SearchHits<TaskElasticModel> hits = elasticsearchOperations.search(searchChildTask, TaskElasticModel.class);

        return new PageImpl<>(
                hits.stream().map(hit -> modelMapper.map(hit.getContent(), TaskResponse.class)).toList(),
                pageable,
                hits.getTotalHits());
    }

    public List<TaskElasticModel> getMyTaskForExport(String type, long userId) {

        boolean isAdmin = userService.checkUserRole(userId, "admin");

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

        org.springframework.data.elasticsearch.core.query.Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(
                        b -> b.must(m -> m.bool(ownQuery))
                ))
                .build();

        SearchHits<TaskElasticModel> hits = elasticsearchOperations.search(query, TaskElasticModel.class);

        return hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public void reindexAllTask() {

        taskElasticRepository.deleteAll();

        taskMapper.mapForIndexList(taskRepository.findAll()).forEach(taskElasticRepository::save);
    }
}
