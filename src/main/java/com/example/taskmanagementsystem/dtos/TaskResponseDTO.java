package com.example.taskmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskResponseDTO {
    int taskId;
    String title;
    String description;
    String status;
    String priority;
    LocalDate dueDate;
}
