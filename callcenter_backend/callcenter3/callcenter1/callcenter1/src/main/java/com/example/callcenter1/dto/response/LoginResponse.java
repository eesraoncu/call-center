package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private String role;
    private Integer operatorId; // Yeni eklenen alan

    // Gerekirse constructor ekle
    public LoginResponse(String token, String email, String role, Integer operatorId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.operatorId = operatorId;
    }
}
