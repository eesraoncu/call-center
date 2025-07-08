package com.example.callcenter1.model.call;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_call")
public class CustomerCall {

    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "call_id", nullable = false)
    private Integer callId;

    // Getter ve Setter'lar

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }
}
