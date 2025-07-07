package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class AdminResponse {
    private Integer adminId;
    private Integer operatorId;
    private Integer logId;
    private Integer roleId;
    private String adminName;
    private String adminSurname;
    private String adminEmail;
    // private String adminPassword; // Güvenlik için genellikle eklenmez!
}
