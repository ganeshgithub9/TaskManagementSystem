package com.example.taskmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    private String name;
    private String mail;
    private String password;
    private String role;
}
