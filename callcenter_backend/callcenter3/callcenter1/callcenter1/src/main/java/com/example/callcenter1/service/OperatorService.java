package com.example.callcenter1.service;

import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.repository.operator.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public List<Operator> findAll() {
        return operatorRepository.findAll();
    }

    public Operator findById(Integer id) {
        return operatorRepository.findById(id).orElse(null);
    }

    public boolean existsById(Integer id) {
        return operatorRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return operatorRepository.existsByOperatorEmail(email);
    }

    public Operator save(Operator operator) {
        return operatorRepository.save(operator);
    }

    public void delete(Integer id) {
        operatorRepository.deleteById(id);
    }
}