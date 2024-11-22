package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Role;
import com.example.taskmanagementsystem.exceptions.RoleExistsException;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;

public interface RoleService {
    public Role addRole(String  role) throws RoleExistsException;
    public Role findByRole(String role) throws RoleNotFoundException;
}
