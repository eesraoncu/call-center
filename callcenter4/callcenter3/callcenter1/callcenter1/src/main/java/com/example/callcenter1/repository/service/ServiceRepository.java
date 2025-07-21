package com.example.callcenter1.repository.service;

import com.example.callcenter1.model.service.Service; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    // Optional<Service> findByServiceType(String serviceType);
}