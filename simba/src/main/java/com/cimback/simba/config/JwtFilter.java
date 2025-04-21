package com.cimback.simba.config;

import java.io.IOException;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cimback.simba.service.JWTservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JWTservice JWTservice;

    @Autowired
    ApplicationContext context;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Implement JWT validation logic here
        filterChain.doFilter(request, response);
        String authHader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHader != null && authHader.startsWith("Bearer ")) {
            token = authHader.substring(7);
            username = JWTservice.extractUsername(token);
            
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validate the token and set authentication in the security context
            if (JWTservice.validateToken(token, username)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
}
