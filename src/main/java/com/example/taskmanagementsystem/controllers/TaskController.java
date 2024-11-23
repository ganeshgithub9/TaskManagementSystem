package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dtos.AssignedUser;
import com.example.taskmanagementsystem.dtos.TaskRequestDTO;
import com.example.taskmanagementsystem.dtos.TaskResponseDTO;
import com.example.taskmanagementsystem.entities.Task;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.example.taskmanagementsystem.services.TaskService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskController {

    TaskService taskService;
    private final ModelMapper modelMapper;

    TaskController(@Autowired TaskService taskService,
                   ModelMapper modelMapper){
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequestDTO taskBody){
        System.out.println(taskBody.getDueDate());
        Task task=taskService.createTask(taskBody);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(){
        List<Task> tasks=taskService.getTasks();
        List<TaskResponseDTO> list= tasks.stream().map((element) -> modelMapper.map(element, TaskResponseDTO.class)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) throws TaskNotFoundException {
        Task task=taskService.deleteTask(id);
        if(task==null)
            return new ResponseEntity<>("Task Not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Task Deleted", HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task taskBody, @PathVariable int id) throws TaskNotFoundException {
        Task task=taskService.updateTask(taskBody,id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TASK_MANAGER','ADMIN')")
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<String> assignUserToTask(@PathVariable int id, @RequestBody AssignedUser user) throws TaskNotFoundException, UserNotFoundException {
        Task task=taskService.assignUserToTask(user.getId(),id);
        return new ResponseEntity<>("Task "+task.getTaskId()+" assigned to user "+task.getAssignedTo().getName(), HttpStatus.OK);
    }

    @GetMapping("/tasks/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(@RequestParam(value = "status",required = false) String status,@RequestParam(value = "priority",required = false) String priority,@RequestParam(value = "dueDate",required = false) LocalDate dueDate){
        List<Task> tasks=taskService.filterTasks(status!=null?status.toUpperCase():null,priority!=null?priority.toUpperCase():null,dueDate);
        List<TaskResponseDTO> list= tasks.stream().map((element) -> modelMapper.map(element, TaskResponseDTO.class)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
