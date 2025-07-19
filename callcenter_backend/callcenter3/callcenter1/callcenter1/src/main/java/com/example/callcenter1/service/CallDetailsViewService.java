package com.example.callcenter1.service;

import com.example.callcenter1.model.call.CallDetailsView;
import com.example.callcenter1.repository.call.CallDetailsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallDetailsViewService {
    @Autowired
    private CallDetailsViewRepository repository;

    public List<CallDetailsView> getAllCallDetails() {
        return repository.findAll();
    }
} 