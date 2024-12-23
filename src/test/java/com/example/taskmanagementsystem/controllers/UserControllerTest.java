package com.example.taskmanagementsystem.controllers;


import com.example.taskmanagementsystem.configurations.TestWebConfig;
import com.example.taskmanagementsystem.dtos.UserRequestDTO;
import com.example.taskmanagementsystem.dtos.UserResponseDTO;
import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.exceptions.RoleNotFoundException;
import com.example.taskmanagementsystem.filters.JwtRequestFilter;
import com.example.taskmanagementsystem.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(value = UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {


    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNothing_WhenHi_ThenReturnsHi() throws Exception {

        mockMvc.perform(get("/api/hi"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi"));
    }

    @Test
    void givenUserDetails_WhenRegisterUser_ThenReturnsUser() throws Exception {
        User user = new User();
        UserResponseDTO savedUser = new UserResponseDTO();
        savedUser.setId(2);savedUser.setMail("test@test.com");savedUser.setName("Test");

        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(savedUser);

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mail\":\"test@test.com\",\"name\":\"Test\",\"password\":\"test\",\"role\":\"user\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.mail").value("test@test.com"))
                .andExpect(jsonPath("$.name").value("Test"));

    }

    @Test
    void givenInvalidRole_WhenRegisterUser_ThenThrowsRoleNotFoundException() throws Exception {
//        User user = new User();
//        UserResponseDTO savedUser = new UserResponseDTO();
//        savedUser.setId(2);savedUser.setMail("test@test.com");savedUser.setName("Test");

        when(userService.registerUser(any(UserRequestDTO.class))).thenThrow(new RoleNotFoundException("Role user not found"));
        //when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(savedUser);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mail\":\"test@test.com\",\"name\":\"Test\",\"password\":\"test\",\"role\":\"user\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Role user not found"));
    }

    @Test
    void givenUserDetails_WhenRegisterUser_ThenThrowsMappingException() throws Exception {
        User user = new User();
        UserResponseDTO savedUser = new UserResponseDTO();
        savedUser.setId(2);savedUser.setMail("test@test.com");savedUser.setName("Test");

        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenThrow(new MappingException(new ArrayList<>()));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mail\":\"test@test.com\",\"name\":\"Test\",\"password\":\"test\",\"role\":\"user\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Modelmapper mapping errors"));
    }
}
