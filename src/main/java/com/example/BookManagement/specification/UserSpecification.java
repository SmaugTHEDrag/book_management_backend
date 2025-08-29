package com.example.BookManagement.specification;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/*
 * Builds dynamic filtering conditions for User records.
 */
public class UserSpecification {

    /**
     * Create a dynamic WHERE clause based on search filters from UserFilterForm.
     *
     * @param form The filter data (username, email, role, ID range).
     * @return A Specification object for querying the database.
     */
    public static Specification<User> buildWhere(UserFilterForm form) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search by username
            if(form.getUsernameSearch() != null && !form.getUsernameSearch().isEmpty()){
                String value = "%" + form.getUsernameSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("username"), value));
            }
            // Search by email
            if(form.getEmailSearch() != null && !form.getEmailSearch().isEmpty()){
                String value = "%" + form.getEmailSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("email"), value));
            }
            // Search by role
            if(form.getRoleSearch() != null && !form.getRoleSearch().isEmpty()){
                String value = "%" + form.getRoleSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("role"), value));
            }

            // Filter by minimum ID
            if (form.getMinId() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }

            // Filter by maximum ID
            if (form.getMaxId() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }

            // Combine all filters with AND
            return criteriaBuilder.and(predicates.toArray((new jakarta.persistence.criteria.Predicate[0])));
        };
    }
}
