package com.example.callcenter1.dto.response;

public class OperatorCustomerCountResponse {
    private Integer operatorId;
    private Long distinctCustomerCount;

    public OperatorCustomerCountResponse(Integer operatorId, Long distinctCustomerCount) {
        this.operatorId = operatorId;
        this.distinctCustomerCount = distinctCustomerCount;
    }

    public Integer getOperatorId() { return operatorId; }
    public void setOperatorId(Integer operatorId) { this.operatorId = operatorId; }

    public Long getDistinctCustomerCount() { return distinctCustomerCount; }
    public void setDistinctCustomerCount(Long distinctCustomerCount) { this.distinctCustomerCount = distinctCustomerCount; }
}