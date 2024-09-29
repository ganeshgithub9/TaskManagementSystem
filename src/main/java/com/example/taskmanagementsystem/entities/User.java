package com.example.taskmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String mail;
    private String name,password;

    @OneToMany(mappedBy = "createdBy")
    List<Task> tasksCreated=new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo")
    List<Task> tasksAssigned=new ArrayList<>();
}
