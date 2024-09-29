package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.entities.User;
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
    CustomUserService(@Autowired PasswordEncoder passwordEncoder, @Autowired ModelMapper modelMapper, @Autowired UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(UserRequestDTO userRequestDTO) {
        User user =modelMapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(Integer id) throws RuntimeException {
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        return user;
    }
}
