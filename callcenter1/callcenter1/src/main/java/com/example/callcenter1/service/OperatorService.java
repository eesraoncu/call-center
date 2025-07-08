package com.example.callcenter1.service;

import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.repository.operator.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public List<Operator> findAll() {
        return operatorRepository.findAll();
    }

    public Operator findById(Integer id) {
        Optional<Operator> operator = operatorRepository.findById(id);
        return operator.orElse(null);
    }

    public Operator save(Operator operator) {
        return operatorRepository.save(operator);
    }

    public Operator update(Integer id, Operator operator) {
        if (operatorRepository.existsById(id)) {
            operator.setOperatorId(id);
            return operatorRepository.save(operator);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (operatorRepository.existsById(id)) {
            operatorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
