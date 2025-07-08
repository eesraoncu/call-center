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

    public List<Operator> getAllOperators() { return operatorRepository.findAll(); }
    public Operator saveOperator(Operator operator) { return operatorRepository.save(operator); }
    public void deleteOperator(Integer id) { operatorRepository.deleteById(id); }
}
