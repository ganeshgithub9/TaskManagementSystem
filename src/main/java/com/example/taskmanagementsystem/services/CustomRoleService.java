package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.Role;
import com.example.taskmanagementsystem.exceptions.RoleExistsException;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;
import com.example.taskmanagementsystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CustomRoleService implements RoleService{

    RoleRepository roleRepository;
    CustomRoleService(@Autowired RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public Role addRole(String role) throws RoleExistsException {
        Role role1=roleRepository.findByName(role);
        if(role1!=null)
            throw new RoleExistsException(role+" already exists");
        Role newRole = new Role();
        newRole.setName(role);
        return roleRepository.save(newRole);
    }

    @Override
    public Role findByRole(String role) throws RoleNotFoundException {
        return Optional.ofNullable(roleRepository.findByName(role)).orElseThrow(()->new RoleNotFoundException(role +" not found"));
    }
}
