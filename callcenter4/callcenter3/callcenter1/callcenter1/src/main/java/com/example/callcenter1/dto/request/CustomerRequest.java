package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.customer.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "Müşteri adı boş olamaz")
    private String customerName;

    @NotBlank(message = "Müşteri soyadı boş olamaz")
    private String customerSurname;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Size(min = 10, max = 15, message = "Telefon numarası 10-15 karakter olmalı")
    private String customerPhoneNumber;

    private String customerExplanation;
    private Boolean customerActive;
    //private Integer serviceId;

    @Email(message = "Geçerli bir email adresi giriniz")
    private String customerEmail;

    private String neighbourhoodExplanation;

    private Integer addressId;

    private Integer operatorId;

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setCustomerSurname(customerSurname);
        customer.setCustomerPhoneNumber(customerPhoneNumber);
        customer.setCustomerExplanation(customerExplanation);
        customer.setCustomerActive(customerActive);
        //customer.setServiceId(serviceId);
        customer.setCustomerEmail(customerEmail);
        customer.setAddressId(addressId);
        customer.setNeighbourhoodExplanation(neighbourhoodExplanation);
        customer.setOperatorId(operatorId); // operatorId set
        return customer;
    }
}