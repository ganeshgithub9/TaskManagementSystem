package com.example.taskmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
    private Integer id;
    private String name,mail;
}
