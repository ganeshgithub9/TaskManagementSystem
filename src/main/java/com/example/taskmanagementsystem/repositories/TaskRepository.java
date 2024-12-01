package com.example.taskmanagementsystem.repositories;

import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.projections.TaskProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    Page<TaskProjection> findAllBy(Pageable pageable);
}
