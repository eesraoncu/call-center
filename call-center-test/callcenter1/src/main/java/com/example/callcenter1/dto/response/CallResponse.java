package com.example.callcenter1.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CallResponse {
    private Integer callId;
    private LocalTime callTime;
    private LocalDate callDate;
}
