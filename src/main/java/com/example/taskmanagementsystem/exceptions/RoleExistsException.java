package com.example.taskmanagementsystem.exceptions;

import jdk.jfr.Category;
import org.springframework.stereotype.Component;


public class RoleExistsException extends Exception{
    public RoleExistsException(String msg){
        super(msg);
    }
}
