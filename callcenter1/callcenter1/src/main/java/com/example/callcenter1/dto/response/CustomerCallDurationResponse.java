package com.example.callcenter1.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CustomerCallDurationResponse {
    private Integer customerId;
    private String customerName;
    private String customerSurname;
    private String customerPhoneNumber;
    private Integer callId;
    private LocalTime callTime;
    private LocalDate callDate;
    private Integer callDuration;
    private String formattedDuration;
    private String totalDuration; // Müşterinin toplam çağrı süresi
    private Integer totalCalls; // Müşterinin toplam çağrı sayısı

    public CustomerCallDurationResponse(Integer customerId, String customerName, String customerSurname, 
                                      String customerPhoneNumber, Integer callId, LocalTime callTime, 
                                      LocalDate callDate, Integer callDuration) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerPhoneNumber = customerPhoneNumber;
        this.callId = callId;
        this.callTime = callTime;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.formattedDuration = formatDuration(callDuration);
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