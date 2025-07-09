package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.call.CallRecords;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallRequest {
    @NotNull(message = "Çağrı zamanı boş olamaz")
    private LocalTime callTime;

    @NotNull(message = "Çağrı tarihi boş olamaz")
    private LocalDate callDate;

    public CallRecords toEntity() {
        CallRecords call = new CallRecords();
        call.setCallTime(this.callTime);
        call.setCallDate(this.callDate);
        return call;
    }
}
