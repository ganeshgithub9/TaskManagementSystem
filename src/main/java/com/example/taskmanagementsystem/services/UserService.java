package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.entities.User;

public interface UserService {
    User registerUser(UserRequestDTO user);

    User getUser(Integer id);
}
