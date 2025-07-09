package com.example.callcenter1.model.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "service_explanation", nullable = false)
    private String serviceExplanation;

    // Getter ve Setter'lar

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceExplanation() {
        return serviceExplanation;
    }

    public void setServiceExplanation(String serviceExplanation) {
        this.serviceExplanation = serviceExplanation;
    }
}
