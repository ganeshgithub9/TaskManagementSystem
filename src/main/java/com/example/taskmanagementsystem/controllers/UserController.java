package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dtos.UserAuthenticationRequestDTO;
import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.dtos.UserResponseDTO;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;
import com.example.taskmanagementsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    ModelMapper modelMapper;
    UserController(@Autowired UserService userService, @Autowired ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) throws RoleNotFoundException {
        User user=userService.registerUser(userRequestDTO);
        UserResponseDTO userResponseDTO=modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<UserResponseDTO> authenticateUser(@RequestBody UserAuthenticationRequestDTO userAuthenticationRequestDTO) {
//        User user=userService.registerUser(userRequestDTO);
//        UserResponseDTO userResponseDTO=modelMapper.map(user, UserResponseDTO.class);
//        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
//    }

    @PostMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Integer id) throws RuntimeException {
        User user=userService.getUser(id);
        UserResponseDTO userResponseDTO=modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRunTimeException(RuntimeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
