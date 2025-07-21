package com.example.callcenter1.model.call;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_records")
public class CallRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id")
    private Integer callId;

    @Column(name = "call_time", nullable = false)
    private LocalTime callTime;

    @Column(name = "call_date", nullable = false)
    private LocalDate callDate;

    @Column(name = "call_duration", nullable = false)
    private Integer callDuration; // Saniye cinsinden çağrı süresi

    @Column(name = "service_id")
    private Integer serviceId;

    // Getter ve Setter'lar

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    public LocalTime getCallTime() {
        return callTime;
    }

    public void setCallTime(LocalTime callTime) {
        this.callTime = callTime;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
