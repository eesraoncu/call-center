package com.example.callcenter1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.callcenter1.model.operator.OperatorCustomer;
import com.example.callcenter1.repository.call.CallRecordsRepository;
import com.example.callcenter1.repository.customer.CustomerRepository;
import com.example.callcenter1.repository.operator.OperatorCustomerRepository;
import com.example.callcenter1.repository.operator.OperatorRepository;

@Service
public class OperatorCustomerService {
    @Autowired
    private OperatorCustomerRepository operatorCustomerRepository;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CallRecordsRepository callRecordsRepository;

    public ResponseEntity<?> addRelation(Integer operatorId, Integer customerId, Integer callId) {
        if (!operatorRepository.existsById(operatorId)) {
            return ResponseEntity.badRequest().body("Geçersiz operatorId: " + operatorId);
        }
        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.badRequest().body("Geçersiz customerId: " + customerId);
        }
        if (!callRecordsRepository.existsById(callId)) {
            return ResponseEntity.badRequest().body("Geçersiz callId: " + callId);
        }
        if (operatorCustomerRepository.existsByOperatorIdAndCustomerIdAndCallId(operatorId, customerId, callId)) {
            return ResponseEntity.badRequest().body("Bu ilişki zaten mevcut.");
        }
        OperatorCustomer relation = new OperatorCustomer();
        relation.setOperatorId(operatorId);
        relation.setCustomerId(customerId);
        relation.setCallId(callId);
        operatorCustomerRepository.save(relation);
        return ResponseEntity.ok(relation);
    }
    public long getDistinctCustomerCountByOperatorId(Integer operatorId) {
        return operatorCustomerRepository.countDistinctCustomerByOperatorId(operatorId);
    }
} 