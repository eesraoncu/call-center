package com.example.callcenter1.service;

import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.operator.Role;
import com.example.callcenter1.repository.admin.AdminRepository;
import com.example.callcenter1.repository.log.LogRepository;
import com.example.callcenter1.repository.operator.OperatorRepository;
import com.example.callcenter1.repository.operator.RoleRepository;
import com.example.callcenter1.dto.request.AdminRequest;
import com.example.callcenter1.dto.response.AdminResponse;
import com.example.callcenter1.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final OperatorRepository operatorRepository;
    private final LogRepository logRepository;
    private final RoleRepository roleRepository;

    public AdminService(AdminRepository adminRepository,
                        OperatorRepository operatorRepository,
                        LogRepository logRepository,
                        RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.operatorRepository = operatorRepository;
        this.logRepository = logRepository;
        this.roleRepository = roleRepository;
    }

    public AdminResponse createAdmin(AdminRequest request) {
        Admin admin = new Admin();
        admin.setAdminName(request.getAdminName());
        admin.setAdminSurname(request.getAdminSurname());
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPassword(request.getAdminPassword());

        // Operator kontrolü
        Operator operator = operatorRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found"));
        admin.setOperatorId(operator.getOperatorId());

        // Log kontrolü
        Log log = logRepository.findById(request.getLogId())
                .orElseThrow(() -> new ResourceNotFoundException("Log not found"));
        admin.setLogId(log.getLogId());

        // Role kontrolü
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        admin.setRoleId(role.getRoleId());

        Admin savedAdmin = adminRepository.save(admin);
        return convertToResponse(savedAdmin);
    }

    public AdminResponse getAdminById(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        return convertToResponse(admin);
    }

    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AdminResponse updateAdmin(Integer adminId, AdminRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        admin.setAdminName(request.getAdminName());
        admin.setAdminSurname(request.getAdminSurname());
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPassword(request.getAdminPassword());

        Operator operator = operatorRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found"));
        admin.setOperatorId(operator.getOperatorId());

        Log log = logRepository.findById(request.getLogId())
                .orElseThrow(() -> new ResourceNotFoundException("Log not found"));
        admin.setLogId(log.getLogId());

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        admin.setRoleId(role.getRoleId());

        Admin updatedAdmin = adminRepository.save(admin);
        return convertToResponse(updatedAdmin);
    }

    public void deleteAdmin(Integer id) {
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    private AdminResponse convertToResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        response.setAdminId(admin.getAdminId());
        response.setOperatorId(admin.getOperatorId());
        response.setLogId(admin.getLogId());
        response.setRoleId(admin.getRoleId());
        response.setAdminName(admin.getAdminName());
        response.setAdminSurname(admin.getAdminSurname());
        response.setAdminEmail(admin.getAdminEmail());
        return response;
    }
}
