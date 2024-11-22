package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dtos.AddRoleRequest;
import com.example.taskmanagementsystem.entities.Role;
import com.example.taskmanagementsystem.exceptions.RoleExistsException;
import com.example.taskmanagementsystem.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController {

    RoleService roleService;
    public RoleController(@Autowired RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<String> addRole(@RequestBody AddRoleRequest request) throws RoleExistsException {
        String roleUpperCase=request.getRole().toUpperCase();
        Role role1=roleService.addRole(roleUpperCase);
        return new ResponseEntity<>(roleUpperCase+" created", HttpStatus.OK);
    }

    @ExceptionHandler(RoleExistsException.class)
    public ResponseEntity<String> handleRoleExistsException(RoleExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
