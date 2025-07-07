package com.example.callcenter1.model.operator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operator_customer")
public class OperatorCustomer {

    @Id
    @Column(name = "operator_id")
    private Integer operatorId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    // Getter ve Setter'lar

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
}
