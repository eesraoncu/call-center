package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.log.Log;
import lombok.Data;

@Data
public class LogResponse {
    private Integer logId;
    private String logDescription;
    private Integer operatorId;
    private Integer customerId;
    private Integer responseStatus;
    private String method;
    private String uri;
    private String requestBody;
    private String responseBody;
    private java.time.LocalDateTime logDatetime;

    public LogResponse(Log log) {
        this.logId = log.getLogId();
        this.logDescription = log.getLogDescription();
        this.operatorId = log.getOperatorId();
        this.customerId = log.getCustomerId();
        this.method = log.getMethod();
        this.uri = log.getUri();
        this.requestBody = log.getRequestBody();
        this.responseBody = log.getResponseBody();
        this.logDatetime = log.getLogDatetime();
        this.responseStatus = log.getResponseStatus();
    }
}
