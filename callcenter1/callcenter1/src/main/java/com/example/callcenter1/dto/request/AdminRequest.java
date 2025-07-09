package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.admin.Admin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminRequest {
    @NotNull(message = "OperatorId boş olamaz")
    private Integer operatorId;

    @NotNull(message = "LogId boş olamaz")
    private Integer logId;

    @NotNull(message = "RoleId boş olamaz")
    private Integer roleId;

    @NotBlank(message = "Admin adı boş olamaz")
    private String adminName;

    @NotBlank(message = "Admin soyadı boş olamaz")
    private String adminSurname;

    @Email(message = "Geçerli bir email adresi giriniz")
    private String adminEmail;

    @NotBlank(message = "Şifre boş olamaz")
    private String adminPassword;

    public Admin toEntity() {
        Admin admin = new Admin();
        admin.setOperatorId(operatorId);
        admin.setLogId(logId);
        admin.setRoleId(roleId);
        admin.setAdminName(adminName);
        admin.setAdminSurname(adminSurname);
        admin.setAdminEmail(adminEmail);
        admin.setAdminPassword(adminPassword);
        return admin;
    }
}
