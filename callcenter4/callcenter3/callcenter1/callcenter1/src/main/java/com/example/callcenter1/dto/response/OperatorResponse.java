package com.example.callcenter1.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperatorResponse {
    private Integer operatorId;
    private String operatorName;
    private String operatorSurname;
    private String operatorEmail;
    private String operatorPhoneNumber;
    private Integer customerId;
    //private boolean securityApproved;
    private LocalDateTime createdAt;
}