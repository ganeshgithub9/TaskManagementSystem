package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dtos.UserAuthenticationRequestDTO;
import com.example.taskmanagementsystem.utilities.CookieUtil;
import com.example.taskmanagementsystem.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;
    AuthenticationController(@Autowired AuthenticationManager authenticationManager, @Autowired JwtUtil jwtUtil, @Autowired UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

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
        Cookie cookie=CookieUtil.createJwtCookie(jwt,3600); //Use ResponseCookie instead of Cookie
        String cookieString=CookieUtil.cookieToString(cookie);
        headers.set(HttpHeaders.SET_COOKIE,cookieString+"; SameSite=Strict");
        return new ResponseEntity<>("Authenticated", headers, HttpStatus.OK);
    }

    @PostMapping("/logoutt")
    public ResponseEntity<String> logout(){
        System.out.println("Entered /logout endpoint");
        Cookie cookie=CookieUtil.createJwtCookie(null,0);
        String cookieString=CookieUtil.cookieToString(cookie);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE,cookieString);
        return new ResponseEntity<>("Logged out successfully",headers, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

