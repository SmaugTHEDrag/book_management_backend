package com.example.BookManagement.specification;

import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/*
 * Builds dynamic filtering conditions for Book queries.
 */
public class BookSpecification {

    // Create a dynamic WHERE clause based on filters from BookFilterForm.
    public static Specification<Book> buildWhere(BookFilterForm form) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Common search: match title OR author
            if(form.getSearch() != null && !form.getSearch().isEmpty()){
                String value = "%" + form.getSearch().toLowerCase() + "%";

                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), value);
                Predicate authorPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")), value);

                // Add condition: title OR author
                predicates.add(criteriaBuilder.or(titlePredicate, authorPredicate));
            }
            // If no common search, check title and author separately
            else {
                // Search by title
                if(form.getTitleSearch() != null && !form.getTitleSearch().isEmpty()){
                    String value = "%" + form.getTitleSearch().toLowerCase() + "%";
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")), value));
                }
                // Search by author
                if(form.getAuthorSearch() != null && !form.getAuthorSearch().isEmpty()){
                    String value = "%" + form.getAuthorSearch().toLowerCase() + "%";
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("author")), value));
                }
            }
            // Search by category
            if(form.getCategorySearch() != null && !form.getCategorySearch().isEmpty()){
                String value = "%" + form.getCategorySearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("category"), value));
            }

            // Filter by minimum ID
            if (form.getMinId() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }

            // Filter by maximum ID
            if (form.getMaxId() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }

            // Combine all conditions with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

