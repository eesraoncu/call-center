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
    private Integer callDuration;
    private String formattedDuration; // Formatlanmış süre (örn: 5 dakika 30 saniye)

    public CallResponse(CallRecords call) {
        this.callId = call.getCallId();
        this.callTime = call.getCallTime();
        this.callDate = call.getCallDate();
        this.callDuration = call.getCallDuration();
        this.formattedDuration = formatDuration(call.getCallDuration());
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
