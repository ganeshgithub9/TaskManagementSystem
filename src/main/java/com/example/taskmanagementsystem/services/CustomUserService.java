package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.entities.Role;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;
import com.example.taskmanagementsystem.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class CustomUserService implements UserService{
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;
    UserRepository userRepository;
    RoleService roleService;
    CustomUserService(@Autowired PasswordEncoder passwordEncoder, @Autowired ModelMapper modelMapper, @Autowired UserRepository userRepository,@Autowired RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User registerUser(UserRequestDTO userRequestDTO) throws RoleNotFoundException {
        User user =modelMapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        String roleUpper=userRequestDTO.getRole().toUpperCase();
        Role role=roleService.findByRole(roleUpper);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User getUser(Integer id) throws RuntimeException {
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        return user;
    }
}
