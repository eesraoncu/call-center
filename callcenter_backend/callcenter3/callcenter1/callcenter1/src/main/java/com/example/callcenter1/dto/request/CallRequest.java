package com.example.callcenter1.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.example.callcenter1.model.call.CallRecords;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallRequest {
    @NotNull(message = "Çağrı zamanı boş olamaz")
    private String callTime;

    @NotNull(message = "Çağrı tarihi boş olamaz")
    private String callDate;

    @NotNull(message = "Çağrı süresi boş olamaz")
    @Min(value = 1, message = "Çağrı süresi en az 1 saniye olmalıdır")
    private Integer callDuration;

    @NotNull(message = "Müşteri ID boş olamaz")
    private Integer customerId;

    @NotNull(message = "Servis ID boş olamaz")
    private Integer serviceId;

    public CallRecords toEntity() {
        CallRecords call = new CallRecords();
        
        // Parse callTime with multiple format support
        LocalTime parsedTime = parseTime(callTime);
        call.setCallTime(parsedTime);
        
        // Parse callDate with multiple format support
        LocalDate parsedDate = parseDate(callDate);
        call.setCallDate(parsedDate);
        
        call.setCallDuration(this.callDuration);
        call.setServiceId(this.serviceId);
        return call;
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Zaman boş olamaz");
        }

        // Try different time formats
        String[] timeFormats = {"HH:mm:ss", "HH:mm", "H:mm:ss", "H:mm"};
        
        for (String format : timeFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalTime.parse(timeStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        
        throw new IllegalArgumentException("Geçersiz zaman formatı: " + timeStr + ". Beklenen formatlar: HH:mm:ss, HH:mm");
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Tarih boş olamaz");
        }

        // Try different date formats
        String[] dateFormats = {"yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy"};
        
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        
        throw new IllegalArgumentException("Geçersiz tarih formatı: " + dateStr + ". Beklenen formatlar: yyyy-MM-dd, dd/MM/yyyy");
    }
}
