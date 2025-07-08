package com.example.callcenter1.service;

import com.example.callcenter1.repository.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.example.callcenter1.model.service.Service> getAllServices() { return serviceRepository.findAll(); }
    public com.example.callcenter1.model.service.Service saveService(com.example.callcenter1.model.service.Service service) { return serviceRepository.save(service); }
    public void deleteService(Integer id) { serviceRepository.deleteById(id); }
}
