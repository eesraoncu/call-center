package com.example.callcenter1.service;

import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.repository.call.CallRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {
    @Autowired
    private CallRecordsRepository callRecordsRepository;

    public List<CallRecords> findAll() {
        return callRecordsRepository.findAll();
    }

    public CallRecords findById(Integer id) {
        Optional<CallRecords> call = callRecordsRepository.findById(id);
        return call.orElse(null);
    }

    public CallRecords save(CallRecords call) {
        return callRecordsRepository.save(call);
    }

    public CallRecords update(Integer id, CallRecords call) {
        if (callRecordsRepository.existsById(id)) {
            call.setCallId(id);
            return callRecordsRepository.save(call);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (callRecordsRepository.existsById(id)) {
            callRecordsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
