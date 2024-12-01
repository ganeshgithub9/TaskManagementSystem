package com.example.taskmanagementsystem.projections;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public interface TaskProjection {
    Integer getTaskId();
    String getTitle();
    String getDescription();
    LocalDate getDueDate();
    String getPriority();
    String getStatus();
}
