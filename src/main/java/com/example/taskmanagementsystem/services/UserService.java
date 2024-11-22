package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;

public interface UserService {
    User registerUser(UserRequestDTO user) throws RoleNotFoundException;

    User getUser(Integer id);
}
