package com.example.callcenter1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.CustomerRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.CustomerResponse;
import com.example.callcenter1.model.admin.Admin;
import com.example.callcenter1.model.customer.Customer;
import com.example.callcenter1.repository.admin.AdminRepository;
import com.example.callcenter1.service.CustomerService;
import com.example.callcenter1.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LogService logService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin getCurrentAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepository.findByAdminEmail(email).orElse(null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.findAll().stream()
                .map(CustomerResponse::new)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteriler listelendi", customers, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri bulundu", new CustomerResponse(customer), null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Müşteri bulunamadı", null, "NOT_FOUND"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer customer = request.toEntity();
        customer.setAddressId(request.getAddressId());
        Customer savedCustomer = customerService.save(customer);
        customer.setNeighbourhoodExplanation(request.getNeighbourhoodExplanation());
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri kaydedildi", new CustomerResponse(savedCustomer), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerRequest request) {
        Customer customer = request.toEntity();
        customer.setAddressId(request.getAddressId());
        Customer updatedCustomer = customerService.update(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri güncellendi", new CustomerResponse(updatedCustomer), null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Müşteri bulunamadı", null, "NOT_FOUND"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Integer id) {
        boolean deleted = customerService.delete(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri silindi", null, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Müşteri bulunamadı", null, "NOT_FOUND"));
        }
    }
}