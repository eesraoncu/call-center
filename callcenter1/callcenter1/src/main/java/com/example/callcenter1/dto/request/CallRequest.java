package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.call.CallRecords;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Çağrı süresi boş olamaz")
    @Min(value = 1, message = "Çağrı süresi en az 1 saniye olmalıdır")
    private Integer callDuration;

    public CallRecords toEntity() {
        CallRecords call = new CallRecords();
        call.setCallTime(this.callTime);
        call.setCallDate(this.callDate);
        call.setCallDuration(this.callDuration);
        return call;
    }
}
