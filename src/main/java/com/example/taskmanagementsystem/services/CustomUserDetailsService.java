package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.entities.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    CustomUserDetailsService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<String> roleNames=userRepository.getRoleNames(user.getId());
        roleNames.stream().forEach(System.out::println);
        List<GrantedAuthority> authorities = roleNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableList());
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), authorities);
    }
}

