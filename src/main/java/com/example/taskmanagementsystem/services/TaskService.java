package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getTasks();

    Task deleteTask(int id);
}
