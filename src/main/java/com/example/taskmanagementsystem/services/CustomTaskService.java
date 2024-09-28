package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CustomTaskService implements TaskService{

    TaskRepository taskRepository;

    public CustomTaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task deleteTask(int id) {
        Optional<Task> task=taskRepository.findById(id);
        return task.orElse(null);
    }
}
