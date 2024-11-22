package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.AssignedUser;
import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CustomTaskService implements TaskService{

    TaskRepository taskRepository;
    UserRepository userRepository;
    public CustomTaskService(@Autowired TaskRepository taskRepository, @Autowired UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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

    @Override
    public Task assignUserToTask(int userId, int id) throws TaskNotFoundException, UserNotFoundException{
        Task task=taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task id "+id+" not found"));
        User user1=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User id "+userId+" not found"));
        task.setAssignedTo(user1);
        taskRepository.save(task);
        return task;
    }
}
