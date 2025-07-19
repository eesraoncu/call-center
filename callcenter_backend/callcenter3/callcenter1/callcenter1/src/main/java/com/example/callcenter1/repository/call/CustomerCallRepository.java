package com.example.callcenter1.repository.call;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.callcenter1.model.call.CustomerCall;

@Repository
public interface CustomerCallRepository extends JpaRepository<CustomerCall, Integer> {
    CustomerCall findByCallId(Integer callId);

    void deleteByCallId(Integer callId);
} 