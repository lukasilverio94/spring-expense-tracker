package com.dev.expensetracker.specs;

import com.dev.expensetracker.domain.dto.ExpenseFilterDTO;
import com.dev.expensetracker.domain.entity.Expense;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecifications {

    public static Specification<Expense> withFilters(ExpenseFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
            }

            if (filter.startDatetime() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateTime"), filter.startDatetime()));
            }

            if (filter.endDatetime() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateTime"), filter.endDatetime()));
            }

            if (filter.minAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.minAmount()));
            }

            if (filter.maxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.maxAmount()));
            }

            if (filter.categoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("categoryId"), filter.categoryId()));
            }

            if (filter.categoryName() != null && !filter.categoryName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("category").get("name")), "%" + filter.categoryName().toLowerCase() + "%"));
            }

            if (filter.userId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.userId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
