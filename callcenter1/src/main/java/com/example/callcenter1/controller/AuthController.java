package com.example.callcenter1.controller;

import com.example.callcenter1.model.User;
import com.example.callcenter1.model.Role;
import com.example.callcenter1.repository.UserRepository;
import com.example.callcenter1.repository.RoleRepository;
import com.example.callcenter1.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> userMap) {
        String userName = userMap.get("user_name");
        String userPassword = userMap.get("user_password");
        Integer roleId = Integer.valueOf(userMap.getOrDefault("role_id", "1"));// role_id giriş ekranından kaldırılacak ve role_id değeri veritabanından çekilecek.
        if (userRepository.findByUserName(userName).isPresent()) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("status", "error");
            resp.put("message", "Username already exists");
            return resp;
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(passwordEncoder.encode(userPassword));
        user.setRoleId(roleId);
        user.setUserSurname("");
        user.setUserPhoneNumber("");
        user.setServiceId(0);
        user.setCustomerId(0);
        user.setId(0L);
        userRepository.save(user);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "success");
        resp.put("message", "User registered successfully");
        return resp;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginMap) {
        String userName = loginMap.get("user_name");
        String userPassword = loginMap.get("user_password");
        Map<String, Object> resp = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, userPassword));
            User user = userRepository.findByUserName(userName).orElseThrow();
            Role role = roleRepository.findByRoleId(user.getRoleId()).orElseThrow();
            String token = jwtUtil.generateToken(user.getUserName(), role.getRoleType());
            resp.put("status", "success");
            resp.put("token", token);
        } catch (AuthenticationException e) {
            resp.put("status", "error");
            resp.put("message", "Invalid username or password");
        }
        return resp;
    }


} 