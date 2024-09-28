package com.example.taskmanagementsystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    Integer taskId;
    String title,description,status,priority;
    Date dueDate;
}
