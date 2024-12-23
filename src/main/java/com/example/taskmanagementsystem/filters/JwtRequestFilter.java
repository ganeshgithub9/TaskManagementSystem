package com.example.taskmanagementsystem.filters;

import com.example.taskmanagementsystem.utilities.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtUtil jwtUtil;

    JwtRequestFilter(@Autowired UserDetailsService userDetailsService,@Autowired JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String path = request.getRequestURI();

        // public endpoints also get into custom filters. So, handling them here
//        if ("/authenticate".equals(path)||"/api/register".equals(path)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        final Cookie[] cookies = request.getCookies();
        System.out.println("Inside JWT filter");
        String username = null;
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
            try {
                username = jwtUtil.extractUsername(jwt);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println("Security context set");
                }
            }
        }
        System.out.println("Going to next filter");
        filterChain.doFilter(request, response);
    }
}

