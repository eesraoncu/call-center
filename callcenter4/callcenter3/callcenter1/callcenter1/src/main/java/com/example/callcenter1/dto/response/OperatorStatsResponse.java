package com.example.callcenter1.dto.response;

public class OperatorStatsResponse {
    private String operatorEmail;
    private long customerCount;
    private long callCount;

    public OperatorStatsResponse(String operatorEmail, long customerCount, long callCount) {
        this.operatorEmail = operatorEmail;
        this.customerCount = customerCount;
        this.callCount = callCount;
    }

    public String getOperatorEmail() { return operatorEmail; }
    public void setOperatorEmail(String operatorEmail) { this.operatorEmail = operatorEmail; }
    public long getCustomerCount() { return customerCount; }
    public void setCustomerCount(long customerCount) { this.customerCount = customerCount; }
    public long getCallCount() { return callCount; }
    public void setCallCount(long callCount) { this.callCount = callCount; }
}