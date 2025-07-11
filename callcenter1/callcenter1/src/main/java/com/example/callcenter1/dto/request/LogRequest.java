package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.log.Log;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
    @NotBlank(message = "Log açıklaması boş olamaz")
    private String logDescription;

    public Log toEntity() {
        Log log = new Log();
        log.setLogDescription(this.logDescription);
        return log;
    }
}
