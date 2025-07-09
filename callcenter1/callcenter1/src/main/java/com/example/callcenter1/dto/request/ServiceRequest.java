package com.example.callcenter1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceRequest {

    @NotBlank(message = "Servis tipi boş olamaz")
    private String serviceType;

    @NotBlank(message = "Servis açıklaması boş olamaz")
    private String serviceExplanation;

    // Model entity dönüşümü için helper metot
    public com.example.callcenter1.model.service.Service toEntity() {
        com.example.callcenter1.model.service.Service service = new com.example.callcenter1.model.service.Service();
        service.setServiceType(this.serviceType);
        service.setServiceExplanation(this.serviceExplanation);
        return service;
    }
}
