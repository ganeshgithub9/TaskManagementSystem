package com.example.taskmanagementsystem.specifications;

import com.example.taskmanagementsystem.entities.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class TaskSpecification {
    public static Specification<Task> createSpecification(String status, String priority, LocalDate dueDate){
        return (root,query,criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (priority != null && !priority.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("priority"), priority));
            }

            if (dueDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("dueDate"), dueDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
