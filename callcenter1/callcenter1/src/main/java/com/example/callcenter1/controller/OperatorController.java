package com.example.callcenter1.controller;

import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @GetMapping
    public List<Operator> getAllOperators() {
        return operatorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operator> getOperatorById(@PathVariable Integer id) {
        Operator operator = operatorService.findById(id);
        return operator != null ? ResponseEntity.ok(operator) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Operator createOperator(@RequestBody Operator operator) {
        return operatorService.save(operator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operator> updateOperator(@PathVariable Integer id, @RequestBody Operator operator) {
        Operator updated = operatorService.update(id, operator);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Integer id) {
        boolean deleted = operatorService.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
