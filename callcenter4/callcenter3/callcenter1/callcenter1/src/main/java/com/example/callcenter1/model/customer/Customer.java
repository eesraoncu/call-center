package com.example.callcenter1.model.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @Column(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;

    @Column(name = "customer_explanation", nullable = false)
    private String customerExplanation;

    @Column(name = "customer_surname", nullable = false)
    private String customerSurname;

    @Column(name = "customer_active", nullable = false)
    private Boolean customerActive;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "neighbourhood_explanation")
    private String neighbourhoodExplanation;

    @Column(name = "operator_id")
    private Integer operatorId;

    // Getter ve Setter'lar

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerExplanation() {
        return customerExplanation;
    }

    public void setCustomerExplanation(String customerExplanation) {
        this.customerExplanation = customerExplanation;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public Boolean getCustomerActive() {
        return customerActive;
    }

    public void setCustomerActive(Boolean customerActive) {
        this.customerActive = customerActive;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getNeighbourhoodExplanation() { return neighbourhoodExplanation; }
    public void setNeighbourhoodExplanation(String explanation) { this.neighbourhoodExplanation = explanation; }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}