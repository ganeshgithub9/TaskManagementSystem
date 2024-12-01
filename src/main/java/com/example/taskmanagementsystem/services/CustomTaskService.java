package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.AssignedUser;
import com.example.taskmanagementsystem.dtos.TaskRequestDTO;
import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.example.taskmanagementsystem.projections.TaskProjection;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.specifications.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public Task createTask(TaskRequestDTO task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setPriority(task.getPriority().toUpperCase());
        newTask.setDueDate(task.getDueDate());
        newTask.setStatus(task.getStatus().toUpperCase());
        newTask.setCreatedBy(getUserByAuthentication());
        return taskRepository.save(newTask);
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
        task.setDescription(taskBody.getDescription());
        task.setPriority(taskBody.getPriority().toUpperCase());
        task.setDueDate(taskBody.getDueDate());
        task.setStatus(taskBody.getStatus().toUpperCase());
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

    @Override
    public List<Task> filterTasks(String status, String priority, LocalDate dueDate) {
        Specification<Task> specification=TaskSpecification.createSpecification(status, priority, dueDate);
        System.out.println(LocalTime.now());
        List<Task> tasks=taskRepository.findAll(specification);
        System.out.println(LocalTime.now());
        return tasks;
    }

    @Override
    public Page<TaskProjection> getTasks(Pageable pageable) {
        return taskRepository.findAllBy(pageable);
    }

    User getUserByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userRepository.findByMail(username);
        return user;
    }
}
