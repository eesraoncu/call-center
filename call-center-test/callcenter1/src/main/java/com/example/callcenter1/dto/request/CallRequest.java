package com.example.callcenter1.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CallRequest {
    private LocalTime callTime;
    private LocalDate callDate;
}
