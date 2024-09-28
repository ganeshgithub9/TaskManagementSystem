package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getTasks();

    Task deleteTask(int id) throws TaskNotFoundException;

    Task updateTask(Task taskBody, int id) throws TaskNotFoundException;
}
