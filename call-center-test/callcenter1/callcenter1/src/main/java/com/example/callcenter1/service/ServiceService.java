package com.example.callcenter1.service;

import com.example.callcenter1.repository.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.example.callcenter1.model.service.Service> findAll() {
        return serviceRepository.findAll();
    }

    public com.example.callcenter1.model.service.Service findById(Integer id) {
        Optional<com.example.callcenter1.model.service.Service> service = serviceRepository.findById(id);
        return service.orElse(null);
    }

    public com.example.callcenter1.model.service.Service save(com.example.callcenter1.model.service.Service service) {
        return serviceRepository.save(service);
    }

    public com.example.callcenter1.model.service.Service update(Integer id, com.example.callcenter1.model.service.Service service) {
        if (serviceRepository.existsById(id)) {
            service.setServiceId(id);
            return serviceRepository.save(service);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
