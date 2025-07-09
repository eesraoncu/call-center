package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.log.Log;
import lombok.Data;

@Data
public class LogResponse {
    private Integer logId;
    private String logDescription;

    public LogResponse(Log log) {
        this.logId = log.getLogId();
        this.logDescription = log.getLogDescription();
    }
}
