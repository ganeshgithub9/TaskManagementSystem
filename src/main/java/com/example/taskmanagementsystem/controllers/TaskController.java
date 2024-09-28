package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    TaskService taskService;
    TaskController(@Autowired TaskService taskService){
        this.taskService = taskService;
    }
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task taskBody){
        Task task=taskService.createTask(taskBody);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(){
        List<Task> tasks=taskService.getTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id){
        Task task=taskService.deleteTask(id);
        if(task==null)
            return new ResponseEntity<>("Task Not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Task Deleted", HttpStatus.OK);
    }
}