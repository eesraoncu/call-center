package com.example.callcenter1.dto.request;

public class OperatorCustomerRequest {
    private Integer operatorId;
    private Integer customerId;
    private Integer callId;

    // Getter ve Setter'lar
    public Integer getOperatorId() { return operatorId; }
    public void setOperatorId(Integer operatorId) { this.operatorId = operatorId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getCallId() { return callId; }
    public void setCallId(Integer callId) { this.callId = callId; }
}