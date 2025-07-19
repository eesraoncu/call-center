package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class ServiceResponse {
    private Integer serviceId;
    private String serviceType;
    //private Integer callId;


    public ServiceResponse(com.example.callcenter1.model.service.Service service) {
        this.serviceId = service.getServiceId();
        this.serviceType = service.getServiceType();
        //this.callId = service.getCallId();
    }
}
