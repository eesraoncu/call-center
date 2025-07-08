package com.example.callcenter1.dto.request;

import lombok.Data;

@Data
public class AdminRequest {
    private Integer operatorId;
    private Integer logId;
    private Integer roleId;
    private String adminName;
    private String adminSurname;
    private String adminEmail;
    private String adminPassword;
}
