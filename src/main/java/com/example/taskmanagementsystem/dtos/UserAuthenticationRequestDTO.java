package com.example.taskmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAuthenticationRequestDTO {
    private String mail;
    private String password;
}
