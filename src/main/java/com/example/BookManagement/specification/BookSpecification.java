package com.example.BookManagement.specification;

import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> buildWhere(BookFilterForm form) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search for title
            if(form.getTitleSearch() != null && !form.getTitleSearch().isEmpty()){
                String value = "%" + form.getTitleSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("title"), value));
            }
            // Search for author
            if(form.getAuthorSearch() != null && !form.getAuthorSearch().isEmpty()){
                String value = "%" + form.getAuthorSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("author"), value));
            }
            // Search for category
            if(form.getCategorySearch() != null && !form.getCategorySearch().isEmpty()){
                String value = "%" + form.getCategorySearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("category"), value));
            }

            // Min ID
            if (form.getMinId() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }

            // Max ID
            if (form.getMaxId() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

