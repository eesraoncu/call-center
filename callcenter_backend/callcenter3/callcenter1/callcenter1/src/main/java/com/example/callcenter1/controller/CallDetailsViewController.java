package com.example.callcenter1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.LogRequest;
import com.example.callcenter1.model.call.CallDetailsView;
import com.example.callcenter1.service.CallDetailsViewService;
import com.example.callcenter1.service.LogService;

@RestController
@RequestMapping("/api/call-details")
public class CallDetailsViewController {
    @Autowired
    private CallDetailsViewService service;

    @Autowired
    private LogService logService;

    @GetMapping
    public List<CallDetailsView> getAllCallDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.getAllCallDetails();
    }
} 