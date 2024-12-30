package com.example.taskmanagementsystem.configurations;

import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.services.CustomUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestWebConfig {

    @Bean
    @Profile("test")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests->authorizeRequests
                        .requestMatchers("/api/register", "/authenticate").permitAll()
                        .anyRequest().authenticated());

        System.out.println("test profile security filter chain");
        return http.build();
    }

}

