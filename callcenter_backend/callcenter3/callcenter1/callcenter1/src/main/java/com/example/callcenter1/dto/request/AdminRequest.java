package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.admin.Admin;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AdminRequest {


    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 30, message = "Ad en fazla 30 karakter olabilir")
    private String adminName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(max = 30, message = "Soyad en fazla 30 karakter olabilir")
    private String adminSurname;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    private String adminEmail;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String adminPassword;

    public Admin toEntity() {
        Admin admin = new Admin();
        admin.setAdminName(adminName);
        admin.setAdminSurname(adminSurname);
        admin.setAdminEmail(adminEmail);
        admin.setAdminPassword(adminPassword);
        return admin;
    }
}
