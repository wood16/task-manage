package com.example.taskmanage.dto.request;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class DynamicSpecification<T> implements Specification<T> {

    private final String field;
    private final String operator;
    private final String value;

    public DynamicSpecification(String field, String operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<String> path = root.get(field);

        return switch (operator) {
            case "eq" -> cb.equal(path, value);
            case "ne" -> cb.notEqual(path, value);
            case "gt" -> cb.greaterThan(root.get(field), value);
            case "lt" -> cb.lessThan(root.get(field), value);
            case "contains" -> cb.like(cb.lower(path), "%" + value.toLowerCase() + "%");
            default -> null;
        };
    }
}