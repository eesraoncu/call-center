package com.example.callcenter1.controller;

import com.example.callcenter1.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class deneme {
    @Autowired
    private JwtUtil jwtUtil;

    //endpoint tanımlanacak
    @PostMapping("/demo-verify-int-test")
    public Map<String, Object> testVerifyEndpoint(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Integer> body) {

        System.out.println(">>> /demo-verify-int-test endpoint'ine girildi <<<");

        Map<String, Object> resp = new HashMap<>();
        
        try {
            // JWT token doğrulaması
            String token = authHeader.replace("Bearer ", "");
            if (!jwtUtil.validateToken(token)) {
                resp.put("status", "error");
                resp.put("message", "Geçersiz token");
                return resp;
            }
            
            // Integer değer kontrolü
            Integer value = body.get("value");
            if (value == null) {
                resp.put("status", "error");
                resp.put("message", "Integer değer eksik");
                return resp;
            }
            
            resp.put("status", "success");
            resp.put("message", "demo-verify-int-test endpoint REACHED - Value: " + value);
            resp.put("receivedValue", value);
            return resp;
        } catch (Exception e) {
            resp.put("status", "error");
            resp.put("message", "Bir hata oluştu: " + e.getMessage());
            return resp;
        }
    }


    @PostMapping("/demo-verify-int")
    public Map<String, Object> demoVerifyInt(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Integer> body) {
        Map<String, Object> resp = new HashMap<>();
        try {
            // JWT token doğrulaması
            String token = authHeader.replace("Bearer ", "");
            if (!jwtUtil.validateToken(token)) {
                resp.put("status", "error");
                resp.put("message", "Geçersiz token");
                return resp;
            }
            
            // Integer değer kontrolü
            Integer value = body.get("value");
            if (value == null) {
                resp.put("status", "error");
                resp.put("message", "Integer değer eksik");
                return resp;
            }
            
            resp.put("status", "success");
            resp.put("message", "hello world");
            resp.put("receivedValue", value);
            resp.put("endpoint", "demo-verify-int");
            return resp;
        } catch (Exception e) {
            resp.put("status", "error");
            resp.put("message", "Bir hata oluştu: " + e.getMessage());
            return resp;
        }
    }
}