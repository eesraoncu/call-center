package com.example.callcenter1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceRequest {

    @NotBlank(message = "Servis tipi boş olamaz")
    private String serviceType;
/*
    private Integer callId;

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        //this.callId = callId;
    }*/

    // Model entity dönüşümü için helper metot
    public com.example.callcenter1.model.service.Service toEntity() {
        com.example.callcenter1.model.service.Service service = new com.example.callcenter1.model.service.Service();
        service.setServiceType(this.serviceType);
        //service.setCallId(this.callId);
        return service;
    }
}
