package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.LoginRequest;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.operator.Role;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.repository.operator.OperatorRepository;
import com.example.callcenter1.repository.operator.RoleRepository;
import com.example.callcenter1.repository.admin.AdminRepository;
import com.example.callcenter1.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(   "/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Map<String, Object> resp = new HashMap<>();

        try {
            // Önce admin kontrolü
            Optional<Admin> adminOpt = adminRepository.findByAdminEmail(email);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                if (passwordEncoder.matches(password, admin.getAdminPassword())) {
                    String token = jwtUtil.generateToken(admin.getAdminEmail(), "ADMIN");
                    resp.put("status", "success");
                    resp.put("token", token);
                    resp.put("role", "ADMIN");
                    resp.put("securityApproved", admin.getSecurityApproved());
                    return ResponseEntity.ok(resp);
                }
            }

            // Sonra operatör kontrolü
            Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(email);
            if (operatorOpt.isPresent()) {
                Operator operator = operatorOpt.get();
                if (passwordEncoder.matches(password, operator.getOperatorPassword())) {
                    String token = jwtUtil.generateToken(operator.getOperatorEmail(), "OPERATOR");
                    resp.put("status", "success");
                    resp.put("token", token);
                    resp.put("role", "OPERATOR");
                    resp.put("securityApproved", operator.getSecurityApproved());
                    return ResponseEntity.ok(resp);
                }
            }

            // Hatalı giriş
            resp.put("status", "error");
            resp.put("message", "Invalid email or password");
            return ResponseEntity.badRequest().body(resp);
            
        } catch (Exception e) {
            resp.put("status", "error");
            resp.put("message", "Login işlemi sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(500).body(resp);
        }
    }
}