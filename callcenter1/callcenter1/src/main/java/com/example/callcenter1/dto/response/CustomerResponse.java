package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class CustomerResponse {
    private Integer customerId;
    private String customerName;
    private String customerSurname;
    private String customerPhoneNumber;
    private String customerExplanation;
    private Boolean customerActive;
    private Integer serviceId;
    private String customerEmail;
    private Integer addressId;
}
