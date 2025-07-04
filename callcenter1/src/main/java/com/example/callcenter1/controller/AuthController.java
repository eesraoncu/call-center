package com.example.callcenter1.controller;

import com.example.callcenter1.model.Operator;
import com.example.callcenter1.model.Role;
import com.example.callcenter1.repository.OperatorRepository;
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
    private OperatorRepository operatorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> operatorMap) {
        String operatorName = operatorMap.get("operator_name");
        String operatorPassword = operatorMap.get("operator_password");
        Integer roleId = Integer.valueOf(operatorMap.getOrDefault("role_id", "1"));
        if (operatorRepository.findByOperatorName(operatorName).isPresent()) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("status", "error");
            resp.put("message", "Operator name already exists");
            return resp;
        }
        Operator operator = new Operator();
        operator.setOperatorName(operatorName);
        operator.setOperatorPassword(passwordEncoder.encode(operatorPassword));
        operator.setRoleId(roleId);
        operator.setOperatorSurname("");
        operator.setOperatorPhoneNumber("");
        operator.setServiceId(0);
        operator.setCustomerId(0);
        operator.setId(0L);
        operatorRepository.save(operator);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "success");
        resp.put("message", "Operator registered successfully");
        return resp;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginMap) {
        String operatorName = loginMap.get("operator_name");
        String operatorPassword = loginMap.get("operator_password");
        Map<String, Object> resp = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(operatorName, operatorPassword));
            Operator operator = operatorRepository.findByOperatorName(operatorName).orElseThrow();
            Role role = roleRepository.findByRoleId(operator.getRoleId()).orElseThrow();
            String token = jwtUtil.generateToken(operator.getOperatorName(), role.getRoleType());
            resp.put("status", "success");
            resp.put("token", token);
        } catch (AuthenticationException e) {
            resp.put("status", "error");
            resp.put("message", "Invalid operator name or password");
        }
        return resp;
    }
} 