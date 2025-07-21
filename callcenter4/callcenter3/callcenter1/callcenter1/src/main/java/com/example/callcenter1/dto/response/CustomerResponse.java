package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.customer.Customer;

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
    private String neighbourhoodExplanation;
    private Integer operatorId;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.customerSurname = customer.getCustomerSurname();
        this.customerPhoneNumber = customer.getCustomerPhoneNumber();
        this.customerExplanation = customer.getCustomerExplanation();
        this.customerActive = customer.getCustomerActive();
        this.serviceId = customer.getServiceId();
        this.customerEmail = customer.getCustomerEmail();
        this.addressId = customer.getAddressId();
        this.neighbourhoodExplanation = customer.getNeighbourhoodExplanation();
        this.operatorId = customer.getOperatorId(); // operatorId set
    }
}