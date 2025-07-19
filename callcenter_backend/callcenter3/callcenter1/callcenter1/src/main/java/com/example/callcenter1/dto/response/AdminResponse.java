package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.admin.Admin;
import lombok.Data;

@Data
public class AdminResponse {

    private Integer adminId;
    private String adminName;
    private String adminSurname;
    private String adminEmail;
    private Integer role_id;

    public AdminResponse(Admin admin) {

        this.adminId = admin.getAdminId();
        this.adminName = admin.getAdminName();
        this.adminSurname = admin.getAdminSurname();
        this.adminEmail = admin.getAdminEmail();
        this.role_id = admin.getRoleId();
    }
}
