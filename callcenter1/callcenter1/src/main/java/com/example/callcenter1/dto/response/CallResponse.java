package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.call.CallRecords;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CallResponse {
    private Integer callId;
    private LocalTime callTime;
    private LocalDate callDate;

    public CallResponse(CallRecords call) {
        this.callId = call.getCallId();
        this.callTime = call.getCallTime();
        this.callDate = call.getCallDate();
    }
}
