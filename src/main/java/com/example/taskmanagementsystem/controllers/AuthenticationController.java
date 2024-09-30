package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dtos.UserAuthenticationRequestDTO;
import com.example.taskmanagementsystem.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserAuthenticationRequestDTO authenticationRequest) throws BadCredentialsException {
        System.out.println("Entered /authenticate endpoint");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        System.out.println("Passed authentication");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        System.out.println("Generated token");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        //return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return new ResponseEntity<>("Authenticated", headers, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

