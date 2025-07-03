package com.example.callcenter1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {//JWT token'ının doğrulanması için gereklidir.
        String authHeader = request.getHeader("Authorization");//Authorization: JWT token'ının başlangıç kısmıdır.
        String token = null;//token'ı null olarak atar.
        String username = null;//username'i null olarak atar.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {//Bearer: JWT token'ının başlangıç kısmıdır.
            token = authHeader.substring(7);//Bearer'ın sonrasındaki token'ı alır.
            username = jwtUtil.getUsernameFromToken(token);//JWT token'ının içindeki username'i alır.
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//username'in null olmaması ve authentication'ın null olmaması gereklidir.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);//username'i alır.
            if (jwtUtil.validateToken(token)) {//token'ın geçerli olması gereklidir.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());//userDetails'ı ve yetkilerini alır.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// request'i alır.
                SecurityContextHolder.getContext().setAuthentication(authToken);//authentication'ı alır.
            }
        }
        filterChain.doFilter(request, response);//request'i ve response'u alır.
    }
} 