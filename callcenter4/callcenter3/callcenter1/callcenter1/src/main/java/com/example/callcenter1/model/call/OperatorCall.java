package com.example.callcenter1.model.call;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operator_call")
public class OperatorCall {

    @Id
    @Column(name = "operator_id")
    private Integer operatorId;

    @Column(name = "call_id", nullable = false)
    private Integer callId;

    // Getter ve Setter'lar

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }
}
