package com.example.callcenter1.service;

import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.repository.call.CallRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CallService {
    @Autowired
    private CallRecordsRepository callRecordsRepository;

    public List<CallRecords> getAllCallRecords() { return callRecordsRepository.findAll(); }
    public CallRecords saveCallRecord(CallRecords callRecords) { return callRecordsRepository.save(callRecords); }
    public void deleteCallRecord(Integer id) { callRecordsRepository.deleteById(id); }
}
