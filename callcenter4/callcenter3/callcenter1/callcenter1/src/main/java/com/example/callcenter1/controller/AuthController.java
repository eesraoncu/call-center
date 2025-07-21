package com.example.callcenter1.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.LoginRequest;
import com.example.callcenter1.dto.request.SecurityApprovalRequest;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.repository.admin.AdminRepository;
import com.example.callcenter1.repository.operator.OperatorRepository;
import com.example.callcenter1.repository.operator.RoleRepository;
import com.example.callcenter1.security.JwtUtil;
import com.example.callcenter1.service.LogService;

import jakarta.validation.Valid;

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
    @Autowired
    private LogService logService;

    @PostMapping(   "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Map<String, Object> resp = new HashMap<>();

        try {
            // Önce admin kontrolü
            Optional<Admin> adminOpt = adminRepository.findByAdminEmail(email);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                if (passwordEncoder.matches(password, admin.getAdminPassword())) {

                    // Security approved kontrolü
                    boolean securityApproved = admin.getSecurityApproved() != null && admin.getSecurityApproved();

                    String token = jwtUtil.generateToken(admin.getAdminEmail(), "ADMIN");
                    resp.put("status", "success");
                    resp.put("token", token);
                    resp.put("role", "ADMIN");
                    resp.put("securityApproved", securityApproved);
                    resp.put("email", email);


                    return ResponseEntity.ok(resp);
                }
            }

            // Sonra operatör kontrolü
            Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(email);
            if (operatorOpt.isPresent()) {
                Operator operator = operatorOpt.get();
                if (passwordEncoder.matches(password, operator.getOperatorPassword())) {

                    // Security approved kontrolü
                    boolean securityApproved = operator.getSecurityApproved() != null && operator.getSecurityApproved();

                    String token = jwtUtil.generateToken(operator.getOperatorEmail(), "OPERATOR");
                    resp.put("status", "success");
                    resp.put("token", token);
                    resp.put("role", "OPERATOR");
                    resp.put("securityApproved", securityApproved);
                    resp.put("email", email);
                    resp.put("operatorId", operator.getOperatorId()); // operatorId'yi ekle

                    return ResponseEntity.ok(resp);
                }
            }

            // Hatalı giriş
            // Hatalı login logu oluştur
            Optional<Operator> failedOperatorOpt = operatorRepository.findByOperatorEmail(email);
            if (failedOperatorOpt.isPresent()) {
                Operator failedOperator = failedOperatorOpt.get();
                Log failedLog = new Log();
                failedLog.setOperatorId(failedOperator.getOperatorId());
                failedLog.setLogDescription("Hatalı login denemesi: " + email);
                failedLog.setMethod("POST");
                failedLog.setUri("/login");
                failedLog.setRequestBody("{\"email\":\"" + email + "\"}");
                failedLog.setResponseBody("Invalid email or password");
                failedLog.setResponseStatus(400);
                logService.createLog(failedLog);
            }
            resp.put("status", "error");
            resp.put("message", "Invalid email or password");
            return ResponseEntity.badRequest().body(resp);

        } catch (Exception e) {
            resp.put("status", "error");
            resp.put("message", "Login işlemi sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(500).body(resp);
        }
    }

    @PostMapping("/api/operators/{id}/approve-security")
    public ResponseEntity<Map<String, Object>> approveSecurityAgreement(@Valid @RequestBody SecurityApprovalRequest request) {
        Map<String, Object> resp = new HashMap<>();

        try {
            if (request.getApproved() == null || !request.getApproved()) {
                resp.put("status", "error");
                resp.put("message", "Gizlilik sözleşmesi onaylanmalıdır");
                return ResponseEntity.badRequest().body(resp);
            }

            // Önce admin tablosunda ara
            Optional<Admin> adminOpt = adminRepository.findByAdminEmail(request.getEmail());
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                admin.setSecurityApproved(true);
                adminRepository.save(admin);

                resp.put("status", "success");
                resp.put("message", "Gizlilik sözleşmesi onaylandı");
                return ResponseEntity.ok(resp);
            }

            // Sonra operator tablosunda ara
            Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(request.getEmail());
            if (operatorOpt.isPresent()) {
                Operator operator = operatorOpt.get();
                operator.setSecurityApproved(true);
                operatorRepository.save(operator);

                resp.put("status", "success");
                resp.put("message", "Gizlilik sözleşmesi onaylandı");
                return ResponseEntity.ok(resp);
            }

            resp.put("status", "error");
            resp.put("message", "Kullanıcı bulunamadı");
            return ResponseEntity.badRequest().body(resp);

        } catch (Exception e) {
            resp.put("status", "error");
            resp.put("message", "Onay sırasında hata oluştu: " + e.getMessage());
            return ResponseEntity.status(500).body(resp);
        }
    }
}