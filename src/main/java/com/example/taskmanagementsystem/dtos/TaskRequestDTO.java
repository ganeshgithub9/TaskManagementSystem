package com.example.taskmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Setter
@Getter
public class TaskRequestDTO {
    String title,description,status,priority;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    LocalDate dueDate;
}
