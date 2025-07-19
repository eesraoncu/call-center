package com.example.callcenter1.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.callcenter1.model.call.CallRecords;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CallResponse {
    private Integer callId;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime callTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate callDate;

    private Integer callDuration;
    private String formattedDuration;
    private Integer customerId;
    private Integer serviceId;

    // Constructor sadece CallRecords alır
    public CallResponse(CallRecords call, Integer customerId) {
        this.callId = call.getCallId();
        this.callTime = call.getCallTime();
        this.callDate = call.getCallDate();
        this.callDuration = call.getCallDuration();
        this.formattedDuration = formatDuration(call.getCallDuration());
        this.customerId = customerId; // Doğrudan parametre olarak al
        this.serviceId = call.getServiceId();
    }

    private String formatDuration(Integer seconds) {
        if (seconds == null) return "0 saniye";

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        if (minutes == 0) {
            return remainingSeconds + " saniye";
        } else if (remainingSeconds == 0) {
            return minutes + " dakika";
        } else {
            return minutes + " dakika " + remainingSeconds + " saniye";
        }
    }
}
