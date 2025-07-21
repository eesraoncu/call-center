package com.example.callcenter1.service;

import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElse(null);
    }

    public Admin save(Admin admin) {
        // Şifreyi hashle
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        return adminRepository.save(admin);
    }

    public Admin update(Integer id, Admin admin) {
        if (adminRepository.existsById(id)) {
            admin.setAdminId(id);
            // Şifreyi hashle
            admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
            return adminRepository.save(admin);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
