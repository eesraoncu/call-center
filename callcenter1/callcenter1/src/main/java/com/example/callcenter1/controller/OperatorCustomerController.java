package com.example.callcenter1.controller;

import com.example.callcenter1.dto.response.CustomerResponse;
import com.example.callcenter1.model.customer.Customer;
import com.example.callcenter1.model.operator.OperatorCustomer;
import com.example.callcenter1.repository.customer.CustomerRepository;
import com.example.callcenter1.repository.operator.OperatorCustomerRepository;
import com.example.callcenter1.service.OperatorCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operator-customers")
public class OperatorCustomerController {

    @Autowired
    private OperatorCustomerRepository operatorCustomerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OperatorCustomerService operatorCustomerService;

    // Bir operatörün konuştuğu tüm müşterilerin bilgilerini getir
    @GetMapping("/operator/{operatorId}/customers")
    public List<CustomerResponse> getCustomersByOperator(@PathVariable Integer operatorId) {
        List<Integer> customerIds = operatorCustomerRepository.findCustomerIdsByOperatorId(operatorId);
        List<Customer> customers = customerRepository.findAllById(customerIds);
        return customers.stream().map(CustomerResponse::new).collect(Collectors.toList());
    }

    // Bir çağrıya katılan tüm müşteri ve operatör ilişkilerini getir
    @GetMapping("/call/{callId}")
    public List<OperatorCustomer> getOperatorCustomerByCall(@PathVariable Integer callId) {
        return operatorCustomerRepository.findByCallId(callId);
    }

    // Yeni bir operator-musteri-cagri ilişkisi ekle
    @PostMapping
    public ResponseEntity<?> addOperatorCustomerRelation(@RequestParam Integer operatorId,
                                                        @RequestParam Integer customerId,
                                                        @RequestParam Integer callId) {
        return operatorCustomerService.addRelation(operatorId, customerId, callId);
    }
} 