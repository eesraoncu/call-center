package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.AdminRequest;
import com.example.callcenter1.dto.response.AdminResponse;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
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
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin bulundu", new AdminResponse(admin), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@Valid @RequestBody AdminRequest request) {
        Admin admin = adminService.save(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin oluşturuldu", new AdminResponse(admin), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(@PathVariable Integer id, @Valid @RequestBody AdminRequest request) {
        Admin updated = adminService.update(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin güncellendi", new AdminResponse(updated), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Integer id) {
        boolean deleted = adminService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Admin bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin silindi", null, null));
    }
}
