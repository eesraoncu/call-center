package com.example.callcenter1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.AdminRequest;
import com.example.callcenter1.dto.response.AdminResponse;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.service.AdminService;
import com.example.callcenter1.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LogService logService;

    @GetMapping("/{get}")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getAllAdmins() {
        List<AdminResponse> admins = adminService.findAll()
                .stream()
                .map(AdminResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm adminler getirildi", admins, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(@PathVariable Integer id) {
        Admin admin = adminService.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin bulundu", new AdminResponse(admin), null));
    }

   /* @PostMapping
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@Valid @RequestBody AdminRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Admin admin = request.toEntity();
            //admin.setSecurityApproved(false); // Kayıt sırasında otomatik olarak false
            admin = adminService.save(admin);
            logService.save(new LogRequest(
                "Admin oluşturuldu (id: " + admin.getAdminId() + ") | Ekleyen admin: " + email
            ).toEntity());
            return ResponseEntity.ok(new ApiResponse<>(true, "Admin oluşturuldu", new AdminResponse(admin), null));
        } catch (Exception e) {
            logService.save(new LogRequest(
                "Admin oluşturulamadı | Eklemeye çalışan admin: " + email + " | Hata: " + e.getMessage()
            ).toEntity());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Admin oluşturulurken hata oluştu: " + e.getMessage(), null, "INTERNAL_ERROR"));
        }
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(@PathVariable Integer id, @Valid @RequestBody AdminRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin updated = adminService.update(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin güncellendi", new AdminResponse(updated), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Integer id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean deleted = adminService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin silindi", null, null));
    }
}
