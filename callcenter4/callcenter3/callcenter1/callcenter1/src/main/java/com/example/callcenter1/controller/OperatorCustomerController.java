package com.example.callcenter1.controller;

import com.example.callcenter1.dto.response.OperatorStatsResponse;
import com.example.callcenter1.model.operator.OperatorCustomer;
import com.example.callcenter1.repository.operator.OperatorCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operator-customers")
public class OperatorCustomerController {

    @Autowired
    private OperatorCustomerRepository operatorCustomerRepository;

    // Operatörün e-posta adresiyle konuştuğu müşteri ve toplam arama (call) sayısını getir
    @GetMapping("/stats/by-email/{operatorEmail}")
    public ResponseEntity<?> getOperatorStatsByEmail(@PathVariable String operatorEmail) {
        long customerCount = operatorCustomerRepository.countDistinctCustomerByOperatorEmail(operatorEmail);
        long callCount = operatorCustomerRepository.countCallsByOperatorEmail(operatorEmail);
        return ResponseEntity.ok(new OperatorStatsResponse(operatorEmail, customerCount, callCount));
    }

    // Her arama için yeni kayıt ekler
    @PostMapping("/add-call")
    public ResponseEntity<?> addOperatorCustomerCall(@RequestParam Integer operatorId,
                                                    @RequestParam Integer customerId,
                                                    @RequestParam Integer callId) {
        OperatorCustomer newRelation = new OperatorCustomer();
        newRelation.setOperatorId(operatorId);
        newRelation.setCustomerId(customerId);
        newRelation.setCallId(callId);
        operatorCustomerRepository.save(newRelation);
        return ResponseEntity.ok("Yeni arama kaydı eklendi.");
    }
}