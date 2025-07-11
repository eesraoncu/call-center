package com.example.callcenter1.repository.admin;

import com.example.callcenter1.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
     Optional<Admin> findByAdminEmail(String adminEmail);
}