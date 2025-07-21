package com.example.callcenter1.model.operator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operator_customer")
public class OperatorCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "operator_id", nullable = false)
    private Integer operatorId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "call_id", nullable = false)
    private Integer callId;

    // Getter ve Setter'lar
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
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
