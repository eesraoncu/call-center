package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;         
    private Integer adminId;
    private String name;
    private String surname;      
    private String email;         
    private String message; // Frontend girişten sonra başarılı veya başarısız mesajını eklerse diye koydum.
}
