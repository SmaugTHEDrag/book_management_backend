package com.example.BookManagement.specification;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> buildWhere(UserFilterForm form) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search for username
            if(form.getUsernameSearch() != null && !form.getUsernameSearch().isEmpty()){
                String value = "%" + form.getUsernameSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("username"), value));
            }
            // Search for email
            if(form.getEmailSearch() != null && !form.getEmailSearch().isEmpty()){
                String value = "%" + form.getEmailSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("email"), value));
            }
            // Search for role
            if(form.getRoleSearch() != null && !form.getRoleSearch().isEmpty()){
                String value = "%" + form.getRoleSearch().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(root.get("role"), value));
            }

            // Min ID
            if (form.getMinId() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }

            // Max ID
            if (form.getMaxId() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }

            return criteriaBuilder.and(predicates.toArray((new jakarta.persistence.criteria.Predicate[0])));
        };
    }
}
