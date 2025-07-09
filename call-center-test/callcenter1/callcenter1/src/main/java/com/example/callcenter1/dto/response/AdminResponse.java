package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.admin.Admin;
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

    public AdminResponse(Admin admin) {
        this.adminId = admin.getAdminId();
        this.operatorId = admin.getOperatorId();
        this.logId = admin.getLogId();
        this.roleId = admin.getRoleId();
        this.adminName = admin.getAdminName();
        this.adminSurname = admin.getAdminSurname();
        this.adminEmail = admin.getAdminEmail();
    }
}
