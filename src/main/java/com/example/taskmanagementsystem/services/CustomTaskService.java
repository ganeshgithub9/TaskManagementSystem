package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
    public Task deleteTask(int id) throws TaskNotFoundException {
        Task task=taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task not found"));
        taskRepository.deleteById(id);
        return task;
    }

    @Override
    public Task updateTask(Task taskBody, int id) throws TaskNotFoundException {
        Task task=taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task not found"));
        task.setTaskId(taskBody.getTaskId());
        task.setDescription(taskBody.getDescription());
        task.setPriority(taskBody.getPriority());
        task.setDueDate(taskBody.getDueDate());
        task.setStatus(taskBody.getStatus());
        task.setTitle(taskBody.getTitle());
        return taskRepository.save(task);
    }
}
