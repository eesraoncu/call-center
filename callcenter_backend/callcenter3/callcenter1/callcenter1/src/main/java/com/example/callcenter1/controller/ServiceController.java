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

import com.example.callcenter1.dto.request.LogRequest;
import com.example.callcenter1.dto.request.ServiceRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.ServiceResponse;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.model.service.Service;
import com.example.callcenter1.repository.admin.AdminRepository;
import com.example.callcenter1.service.LogService;
import com.example.callcenter1.service.ServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private LogService logService;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAllServices() {
        List<ServiceResponse> services = serviceService.findAll()
                .stream()
                .map(ServiceResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm servisler getirildi", services, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(@PathVariable Integer id) {
        Service service = serviceService.findById(id);
        if (service == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Servis bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Servis bulundu", new ServiceResponse(service), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(@Valid @RequestBody ServiceRequest request) {
        Service savedService = serviceService.save(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Servis oluşturuldu", new ServiceResponse(savedService), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(@PathVariable Integer id,
                                                                      @Valid @RequestBody ServiceRequest request) {
        Service toUpdate = request.toEntity();
        Service updatedService = serviceService.update(id, toUpdate);
        if (updatedService == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Servis bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Servis güncellendi", new ServiceResponse(updatedService), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Integer id) {
        boolean deleted = serviceService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Servis bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Servis silindi", null, null));
    }
}
