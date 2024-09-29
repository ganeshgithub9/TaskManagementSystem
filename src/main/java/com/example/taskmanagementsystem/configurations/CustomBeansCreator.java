package com.example.taskmanagementsystem.configurations;

import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.dtos.UserResponseDTO;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.TaskNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class CustomBeansCreator {

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(User.class, UserResponseDTO.class);
        modelMapper.createTypeMap(UserRequestDTO.class,User.class);
        return modelMapper;
    }

    @Bean
    @Primary
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B,4);
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
