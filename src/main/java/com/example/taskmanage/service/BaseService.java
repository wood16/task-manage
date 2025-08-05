package com.example.taskmanage.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BaseService {

    public Pageable mapPageable(Map<String, Object> queryParams, Sort sortParam) {
        Sort sort = sortParam != null ? sortParam : Sort.unsorted();
        if (queryParams.containsKey("sortBy")) {

            Sort.Direction sortOrder = Sort.Direction.ASC;
            if (queryParams.containsKey("sortOrder")) {
                sortOrder = Sort.Direction.fromString(queryParams.get("sortOrder").toString());
                queryParams.remove("sortOrder");
            }

            sort = Sort.by(sortOrder, queryParams.get("sortBy").toString());
            queryParams.remove("sortBy");
        }

        int page = 1;
        if (queryParams.containsKey("page")) {
            page = Integer.parseInt(queryParams.get("page").toString());
            queryParams.remove("page");
        }

        int pageSize = 10;
        if (queryParams.containsKey("pageSize")) {
            pageSize = Integer.parseInt(queryParams.get("pageSize").toString());
            queryParams.remove("pageSize");
        }

        return PageRequest.of(Math.max(page - 1, 0), pageSize, sort);
    }
}
